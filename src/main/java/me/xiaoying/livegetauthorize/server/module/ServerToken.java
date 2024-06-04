package me.xiaoying.livegetauthorize.server.module;

import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.module.Module;
import me.xiaoying.livegetauthorize.core.module.Token;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.utils.DateUtil;
import me.xiaoying.livegetauthorize.server.utils.StringUtil;
import me.xiaoying.sql.SqlFactory;
import me.xiaoying.sql.entity.Condition;
import me.xiaoying.sql.entity.ConditionType;
import me.xiaoying.sql.sentence.Update;

import java.util.Date;

/**
 * Token
 */
public class ServerToken implements Token {
    private final String token;
    private String machine;
    private String description;
    private Date save;
    private Date over;
    private Date lastUse;
    private final User owner;
    private final Module module;
    private Date survival = new Date();

    public ServerToken(String token, String machine, String description, Date save, Date over, Date lastUse, User owner, Module module) {
        this.token = token;
        this.machine = machine;
        this.description = description;
        this.save = save;
        this.over = over;
        this.lastUse = lastUse;
        this.owner = owner;
        this.module = module;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public User getOwner() {
        return this.owner;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 Machine
     *
     * @return Machine
     */
    public String getMachine() {
        return this.machine;
    }

    /**
     * 设置 Machine
     *
     * @param machine Machine
     */
    public void setMachine(String machine) {
        this.machine = machine;
        SqlFactory sqlFactory = Application.getSqlFactory();
        Update update = new Update(((ServerModule) this.getModule()).getTable());
        update.set("machine", machine);
        sqlFactory.sentence(update).run();
    }

    @Override
    public Date getSave() {
        return this.save;
    }

    @Override
    public void setSave(Date date) {
        this.save = date;
    }

    @Override
    public Date getOver() {
        return this.over;
    }

    @Override
    public void setOver(Date date) {
        this.over = date;
    }

    @Override
    public Date getLastUse() {
        return this.lastUse;
    }

    @Override
    public void updateLastUse() {
        this.setLastUse(new Date());
    }

    @Override
    public void setLastUse(Date date) {
        SqlFactory sqlFactory = Application.getSqlFactory();
        Update update = new Update(((ServerModule) this.getModule()).getTable());
        update.set("lastUse", DateUtil.dateToString(date, FileConfigConstant.SETTING_DATEFORMAT));
        sqlFactory.sentence(update).condition(new Condition("token", this.getToken(), ConditionType.EQUAL)).run();
    }

    @Override
    public boolean isBind() {
        return !StringUtil.isEmpty(this.machine);
    }

    @Override
    public Module getModule() {
        return this.module;
    }

    @Override
    public boolean expire() {
        if (this.save == null || this.over == null)
            return false;

        return new Date().getTime() - this.over.getTime() > 0;
    }

    @Override
    public boolean isSurvival() {
        return new Date().getTime() - this.survival.getTime() > FileConfigConstant.SETTING_CACHE_TOKEN_TIME;
    }
}