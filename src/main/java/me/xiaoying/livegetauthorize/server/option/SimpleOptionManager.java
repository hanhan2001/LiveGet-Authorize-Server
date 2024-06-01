package me.xiaoying.livegetauthorize.server.option;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.option.Option;
import me.xiaoying.livegetauthorize.core.option.OptionManager;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.constant.ConstantCommon;
import me.xiaoying.sql.SqlFactory;
import me.xiaoying.sql.entity.Column;
import me.xiaoying.sql.sentence.Create;
import me.xiaoying.sql.sentence.Insert;
import me.xiaoying.sql.sentence.Select;

import java.util.*;

/**
 * Manager Option
 */
public class SimpleOptionManager implements OptionManager {
    private final Map<String, Option> knownOptions = new HashMap<>();

    public SimpleOptionManager() {
        LACore.getLogger().info("Initializing option manager...");

        // 初始化数据表
        SqlFactory sqlFactory = Application.getSqlFactory();
        Create create = new Create(ConstantCommon.OPTION_LIST);
        Stack<Column> columns = new Stack<>();
        columns.add(new Column("option_name", "varchar", 255));
        columns.add(new Column("option_value", "longtext", 0));
        create.setColumns(columns);
        sqlFactory.sentence(create).run();

        this.defaultOption();

        this.initialize();
    }

    private void defaultOption() {
        SqlFactory sqlFactory = Application.getSqlFactory();
        if (sqlFactory.getTotalRecord(ConstantCommon.OPTION_LIST) > 0)
            return;

        Insert insert = new Insert(ConstantCommon.OPTION_LIST);
        insert.insert("preview_title", "你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好");
        sqlFactory.sentence(insert).run();
    }

    public void initialize() {
        SqlFactory selectSqlFactory = Application.getSqlFactory();
        Select select = new Select(ConstantCommon.OPTION_LIST);
        selectSqlFactory.sentence(select).run().forEach(record -> this.registerOption(new Option(record.get("option_name").toString(), record.get("option_value").toString())));
    }

    @Override
    public void registerOption(Option option) {
        this.knownOptions.put(option.getName(), option);
    }

    /**
     * 注册 Option
     *
     * @param name Option name
     * @param value Option value
     */
    @Override
    public void registerOption(String name, String value) {
        this.knownOptions.put(name, new Option(name, value));
    }

    /**
     * 取消注册 Option
     *
     * @param name Option name
     */
    @Override
    public void unregisterOption(String name) {
        this.knownOptions.remove(name);
    }

    @Override
    public void unregisterOptions() {
        this.knownOptions.clear();
    }

    /**
     * 获取 Option
     *
     * @param name Option name
     * @return Option
     */
    @Override
    public Option getOption(String name) {
        return this.knownOptions.get(name);
    }

    /**
     * 获取所有 Option
     *
     * @return ArrayList
     */
    @Override
    public List<Option> getOptions() {
        return new ArrayList<>(this.knownOptions.values());
    }
}