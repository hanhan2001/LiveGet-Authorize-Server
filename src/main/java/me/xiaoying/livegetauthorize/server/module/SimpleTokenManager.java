package me.xiaoying.livegetauthorize.server.module;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.module.Module;
import me.xiaoying.livegetauthorize.core.module.Token;
import me.xiaoying.livegetauthorize.core.module.TokenManager;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.utils.DateUtil;
import me.xiaoying.sql.SqlFactory;
import me.xiaoying.sql.entity.Column;
import me.xiaoying.sql.entity.Condition;
import me.xiaoying.sql.entity.ConditionType;
import me.xiaoying.sql.entity.Record;
import me.xiaoying.sql.sentence.Create;
import me.xiaoying.sql.sentence.Insert;
import me.xiaoying.sql.sentence.Select;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/**
 * Manager Token
 */
public class SimpleTokenManager implements TokenManager {
    private final Module module;
    private final Map<String, Token> knownToken = new HashMap<>();

    public SimpleTokenManager(Module module) {
        this.module = module;

        // 初始化数据表
        SqlFactory sqlFactory = Application.getSqlFactory();
        Create create = new Create(((ServerModule) this.getModule()).getTable());
        Stack<Column> columns = new Stack<>();
        columns.add(new Column("token", "varchar", 255));
        columns.add(new Column("uuid", "varchar", 255));
        columns.add(new Column("machine", "varchar", 255));
        columns.add(new Column("description", "varchar", 255));
        columns.add(new Column("save", "varchar", 255));
        columns.add(new Column("over", "varchar", 255));
        create.setColumns(columns);
        sqlFactory.sentence(create).run();

        // 判断是否过期
        LACore.getScheduler().scheduleSyncDelayedTask(null, () -> {
            Iterator<Token> iterator = this.knownToken.values().iterator();
            Token token;
            while (iterator.hasNext() && (token = iterator.next()) != null) {
                if (token.isSurvival())
                    continue;

                iterator.remove();
            }
        });
    }

    @Override
    public Token getToken(String token) {
        if (this.knownToken.containsKey(token))
            return this.knownToken.get(token);

        return this.findToken(token);
    }

    private Token findToken(String token) {
        SqlFactory sqlFactory = Application.getSqlFactory();
        Select select = new Select(((ServerModule) this.getModule()).getTable());
        Stack<Record> records = sqlFactory.sentence(select).condition(new Condition("token", token, ConditionType.EQUAL)).run();
        if (records.isEmpty())
            return null;

        Record record = records.get(0);
        Token token1 = new ServerToken(token,
                DateUtil.stringToDate(record.get("save").toString(),
                        FileConfigConstant.SETTING_DATEFORMAT),
                DateUtil.stringToDate(record.get("over").toString(),
                        FileConfigConstant.SETTING_DATEFORMAT),
                Application.getUserManager().getUserByUUID(record.get("uuid").toString()),
                this.getModule());
        this.knownToken.put(token, token1);
        return token1;
    }

    @Override
    public void create(Token token) {
        SqlFactory sqlFactory = Application.getSqlFactory();
        Insert insert = new Insert(((ServerModule) this.getModule()).getTable());
        insert.insert(token.getToken(), "", "", "");
    }

    @Override
    public void delete(String token) {

    }

    @Override
    public boolean contains(String token) {
        if (this.knownToken.containsKey(token))
            return true;

        if (this.findToken(token) != null)
            return true;

        return false;
    }

    @Override
    public Module getModule() {
        return this.module;
    }
}