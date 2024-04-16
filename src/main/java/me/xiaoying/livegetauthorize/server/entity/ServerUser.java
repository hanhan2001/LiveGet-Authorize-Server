package me.xiaoying.livegetauthorize.server.entity;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.entity.User;

import java.util.Date;

/**
 * User
 */
public class ServerUser implements User {
    private final long qq;
    private final String uuid;
    private final String ip;
    private String password;
    private Date registerTime = null;
    private Date lastLoginTime = null;

    public ServerUser(long qq, String uuid) {
        this(qq, uuid, "", "");
        this.lastLoginTime = new Date();
    }

    public ServerUser(long qq, String uuid, String password, String ip) {
        this.qq = qq;
        this.uuid = uuid;
        this.password = password;
        this.ip = ip;
    }

    @Override
    public boolean hasPermission(String s) {
        return LACore.getServer().getPermissionManager().hasPermission(this, s);
    }

    @Override
    public long getQQ() {
        return this.qq;
    }

    @Override
    public String getUUID() {
        return this.uuid;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getIP() {
        return this.ip;
    }

    @Override
    public Date getRegisterTime() {
        return this.registerTime;
    }

    @Override
    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegisterTime() {
        this.setRegisterTime(new Date());
    }

    public void setRegisterTime(Date date) {
        this.registerTime = date;
    }
}