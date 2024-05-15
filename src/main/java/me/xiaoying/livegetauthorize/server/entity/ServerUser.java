package me.xiaoying.livegetauthorize.server.entity;

import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.permission.Permissible;
import me.xiaoying.livegetauthorize.core.permission.PermissibleBase;
import me.xiaoying.livegetauthorize.core.permission.Permission;
import me.xiaoying.livegetauthorize.core.permission.PermissionAttachment;
import me.xiaoying.livegetauthorize.core.plugin.Plugin;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.utils.DateUtil;
import org.java_websocket.WebSocket;

import java.util.Date;

/**
 * User
 */
public class ServerUser implements User {
    private final long qq;
    private String email;
    private long telephone;
    private final String uuid;
    private final String name;
    private final String password;
    private String ip;
    private final Date registerTime;
    private final Date lastLoginTime;
    private Date survival = new Date();
    private String token;
    private final Permissible permissible = new PermissibleBase(this);
    private WebSocket webSocket;
    private String photoBase64;

    public ServerUser(long qq, String email, String uuid, String name, String password, String ip, Date registerTime, Date lastLoginTime, String photoBase64) {
        this.qq = qq;
        this.email = email;
        this.uuid = uuid;
        this.name = name;
        this.password = password;
        this.ip = ip;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.photoBase64 = photoBase64;
    }

    public ServerUser(long qq, String email, long telephone, String uuid, String name, String password, String ip, Date registerTime, Date lastLoginTime, String photoBase64) {
        this.qq = qq;
        this.email = email;
        this.telephone = telephone;
        this.uuid = uuid;
        this.name = name;
        this.password = password;
        this.ip = ip;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.photoBase64 = photoBase64;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void sendMessage(String message) {
        if (this.webSocket == null || this.webSocket.isClosed())
            return;

        this.webSocket.send(message);
    }

    @Override
    public long getQQ() {
        return this.qq;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public long getTelephone() {
        return this.telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
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

    public void setIP(String ip) {
        this.ip = ip;
    }

    @Override
    public Date getRegisterTime() {
        return this.registerTime;
    }

    @Override
    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    @Override
    public boolean isSurvival() {
        return DateUtil.getDateReduce(new Date(), this.survival) < FileConfigConstant.SETTING_DATA_CACHE_TIME;
    }

    @Override
    public void updateSurvival() {
        this.survival = new Date();
    }

    /**
     * 此 Token 用于网页登录认证，非授权码 Token
     *
     * @return String
     */
    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public void setToken(String s) {
        this.token = s;
    }

    @Override
    public void setPhotoBase64(String base64) {
        this.photoBase64 = base64;
    }

    @Override
    public String getPhotoBase64() {
        return this.photoBase64;
    }

    @Override
    public boolean isPermissionSet(String permission) {
        return this.permissible.isPermissionSet(permission);
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        return this.permissible.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.permissible.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return this.permissible.hasPermission(permission);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return this.permissible.addAttachment(plugin);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        this.permissible.removeAttachment(attachment);
    }

    @Override
    public boolean isOp() {
        return this.permissible.isOp();
    }

    @Override
    public void setOp(boolean value) {
        this.permissible.setOp(value);
    }

    public void setWebsocket(WebSocket websocket) {
        this.webSocket = websocket;
    }
}