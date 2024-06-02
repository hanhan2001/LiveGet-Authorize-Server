package me.xiaoying.livegetauthorize.server.module;

import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.module.Module;
import me.xiaoying.livegetauthorize.core.module.Token;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;

import java.util.Date;

/**
 * Token
 */
public class ServerToken implements Token {
    private final String token;
    private Date save;
    private Date over;
    private final User owner;
    private final Module module;
    private Date survival = new Date();

    public ServerToken(String token, Date save, Date over, User owner, Module module) {
        this.token = token;
        this.save = save;
        this.over = over;
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
    public Module getModule() {
        return this.module;
    }

    @Override
    public boolean overdue() {
        if (this.save == null || this.over == null)
            return false;

        return new Date().getTime() - this.over.getTime() > 0;
    }

    @Override
    public boolean isSurvival() {
        return new Date().getTime() - this.survival.getTime() > FileConfigConstant.SETTING_CACHE_TOKEN_TIME;
    }
}