package me.xiaoying.livegetauthorize.server.user;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.constant.ConstantCommon;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.entity.ServerUser;
import me.xiaoying.livegetauthorize.server.utils.DateUtil;
import me.xiaoying.sql.SqlFactory;
import me.xiaoying.sql.entity.Column;
import me.xiaoying.sql.entity.Condition;
import me.xiaoying.sql.entity.ConditionType;
import me.xiaoying.sql.entity.Record;
import me.xiaoying.sql.sentence.Create;
import me.xiaoying.sql.sentence.Delete;
import me.xiaoying.sql.sentence.Insert;
import me.xiaoying.sql.sentence.Select;

import java.text.DecimalFormat;
import java.util.*;

/**
 * UserService
 */
public class UserService {
    private final Map<Long, User> knownQQUsers = new HashMap<>();
    private final Map<String, User> knownEmailUsers = new HashMap<>();

    public UserService() {
        // 初始化用户表
        SqlFactory sqlFactory = Application.getSqlFactory();
        Create create = new Create(ConstantCommon.TABLE_USER_INFO);
        Stack<Column> columns = new Stack<>();
        columns.add(new Column("qq", "varchar", 12));
        columns.add(new Column("email", "varchar", 255));
        columns.add(new Column("telephone", "varchar", 11));
        columns.add(new Column("password", "varchar", 255));
        columns.add(new Column("uuid", "varchar", 9));
        columns.add(new Column("ip", "varchar", 255));
        columns.add(new Column("registerTime", "varchar", 255));
        columns.add(new Column("lastLoginTime", "varchar", 255));
        create.setColumns(columns);
        sqlFactory.sentence(create).run();

        // 检测过期用户缓存
        LACore.getServer().getScheduler().scheduleSyncRepeatingTask(null, () -> {
            Iterator<User> iterator = this.knownQQUsers.values().iterator();
            Iterator<User> iteratorEmail = this.knownEmailUsers.values().iterator();
            User user;
            while (iterator.hasNext() && (user = iterator.next()) != null) {
                if (user.isSurvival())
                    continue;

                iterator.remove();
            }
            while (iteratorEmail.hasNext() && (user = iteratorEmail.next()) != null) {
                if (user.isSurvival())
                    continue;

                iteratorEmail.remove();
            }
        }, 0L, 2L);
    }

    public User getUser(long qq) {
        if (this.knownQQUsers.get(qq) == null)
            return null;

        return this.findUser("qq", String.valueOf(qq));
    }

    public User getUser(String email) {
        if (this.knownEmailUsers.get(email) == null)
            return null;

        return this.findUser("email", email);
    }

    public User createUser(long qq) {
        return this.createUser(qq, "", "");
    }

    public User createUser(long qq, String email, String password) {
        SqlFactory sqlFactory = Application.getSqlFactory();
        String uuid = new DecimalFormat("000000000").format(sqlFactory.getTotalRecord(ConstantCommon.TABLE_USER_INFO));
        Insert insert = new Insert(ConstantCommon.TABLE_USER_INFO);
        String date = DateUtil.dateToString(new Date(), FileConfigConstant.SETTING_DATEFORMAT);
        insert.insert(String.valueOf(qq), email, "0", password, uuid, "", date, date);
        sqlFactory.sentence(insert);
        User user = new ServerUser(qq, email, uuid, password, "", DateUtil.stringToDate(date, FileConfigConstant.SETTING_DATEFORMAT), DateUtil.stringToDate(date, FileConfigConstant.SETTING_DATEFORMAT));
        this.knownQQUsers.put(qq, user);
        this.knownEmailUsers.put(email, user);
        return user;
    }

    public void deleteUser(User user) {
        if (user.getQQ() != 0)
            this.deleteUser(user.getQQ());
        else
            this.deleteUser(user.getEmail());
    }

    public void deleteUser(long qq) {
        SqlFactory sqlFactory = Application.getSqlFactory();
        Delete delete = new Delete(ConstantCommon.TABLE_USER_INFO);
        sqlFactory.sentence(delete).condition(new Condition("qq", String.valueOf(qq), ConditionType.EQUAL)).run();
    }

    public void deleteUser(String email) {
        SqlFactory sqlFactory = Application.getSqlFactory();
        Delete delete = new Delete(ConstantCommon.TABLE_USER_INFO);
        sqlFactory.sentence(delete).condition(new Condition("email", email, ConditionType.EQUAL)).run();
    }

    /**
     * 在数据库中搜索用户
     *
     * @param key QQ/EMAIL
     * @param value 判断值
     */
    public User findUser(String key, String value) {
        if (!key.equalsIgnoreCase("qq") && !key.equalsIgnoreCase("email"))
            return null;

        SqlFactory sqlFactory = Application.getSqlFactory();
        Select select = new Select(ConstantCommon.TABLE_USER_INFO);
        Stack<Record> records = sqlFactory.sentence(select).condition(new Condition(key, value, ConditionType.EQUAL)).run();
        if (records == null || records.isEmpty())
            return null;

        Record column = records.get(0);
        User user = new ServerUser(Long.parseLong(column.get("qq").toString()),
                column.get("email").toString(),
                Long.parseLong(column.get("telephone").toString()),
                column.get("uuid").toString(),
                column.get("password").toString(),
                column.get("ip").toString(),
                DateUtil.stringToDate(column.get("registerTime").toString(), FileConfigConstant.SETTING_DATEFORMAT),
                DateUtil.stringToDate(column.get("lastLoginTime").toString(), FileConfigConstant.SETTING_DATEFORMAT));

        if (key.equalsIgnoreCase("qq"))
            this.knownQQUsers.put(Long.parseLong(value), user);
        else
            this.knownEmailUsers.put(value, user);
        return user;
    }
}