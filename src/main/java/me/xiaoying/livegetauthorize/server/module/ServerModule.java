package me.xiaoying.livegetauthorize.server.module;

import me.xiaoying.livegetauthorize.core.NamespacedKey;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.module.Module;
import me.xiaoying.livegetauthorize.core.module.TokenManager;
import me.xiaoying.livegetauthorize.core.permission.Permission;
import me.xiaoying.livegetauthorize.core.plugin.Plugin;
import me.xiaoying.livegetauthorize.server.constant.ConstantCommon;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.factory.VariableFactory;
import me.xiaoying.livegetauthorize.server.utils.DateUtil;
import me.xiaoying.livegetauthorize.server.utils.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Module
 */
public class ServerModule implements Module {
    private User owner;
    private final String name;
    private final String description;
    private final String permission;
    private String identification;
    private Date save;
    private Date over;
    private final Map<String, Module> knownModule = new HashMap<>();
    private final TokenManager tokenManager;
    private Module parent;

    /**
     * 构造器
     *
     * @param owner 拥有者
     * @param name 名称
     * @param description 描述
     * @param permission 权限
     */
    public ServerModule(User owner, String name, String description, String identification, String permission) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.identification = identification;
        this.tokenManager = new SimpleTokenManager(this);
    }

    /**
     * 构造器
     *
     * @param owner 拥有者
     * @param name 名称
     * @param description 描述
     * @param permission 权限
     */
    public ServerModule(User owner, String name, String description, String identification, Permission permission) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.permission = permission.getPermission();
        this.identification = identification;
        this.tokenManager = new SimpleTokenManager(this);
    }

    /**
     * 构造器
     *
     * @param owner 拥有者
     * @param name 名称
     * @param description 描述
     * @param permission 权限
     * @param identification 身份码
     * @param save 存储时间
     * @param over 过期时间
     */
    public ServerModule(User owner, String name, String description, String permission, String identification, Date save, Date over) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.identification = identification;
        this.save = save;
        this.over = over;
        this.tokenManager = new SimpleTokenManager(this);
    }

    /**
     * 构造器
     *
     * @param owner 拥有者
     * @param name 名称
     * @param description 描述
     * @param permission 权限
     * @param identification 身份码
     * @param save 存储时间
     * @param over 过期时间
     * @param parent 父Module
     */
    public ServerModule(User owner, String name, String description, String permission, String identification, Date save, Date over, Module parent) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.identification = identification;
        this.save = save;
        this.over = over;
        this.parent = parent;
        this.tokenManager = new SimpleTokenManager(this);
    }

    /**
     * 获取拥有者
     *
     * @return User
     */
    @Override
    public User getOwner() {
        return this.owner;
    }

    /**
     * 获取名称
     *
     * @return String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * 获取描述
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 获取权限
     *
     * @return String
     */
    @Override
    public String getPermission() {
        return this.permission;
    }

    /**
     * 获取身份码
     *
     * @return String
     */
    @Override
    public String getIdentification() {
        return this.identification;
    }

    /**
     * 设置身份码
     *
     * @param identification String
     */
    @Override
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    /**
     * 获取存储时间
     *
     * @return Date
     */
    @Override
    public Date getSave() {
        return this.save;
    }

    /**
     * 获取过期时间
     *
     * @return Date
     */
    @Override
    public Date getOver() {
        return this.over;
    }

    /**
     * 是否过期
     *
     * @return Boolean
     */
    @Override
    public boolean expire() {
        if (this.save == null || this.over == null)
            return false;

        return new Date().getTime() - this.over.getTime() >= 0;
    }

    @Override
    public Module getParent() {
        return this.parent;
    }

    /**
     * 注册子 Module
     *
     * @param module Module
     */
    public void registerChild(Module module) {
        if (module.getName().startsWith("authorizecore:")) {
            this.knownModule.put(module.getName().toLowerCase(Locale.ROOT), module);
            return;
        }

        this.knownModule.put("authorizecore:" + module.getName().toLowerCase(Locale.ROOT), module);
    }

    @Override
    public void registerModuleChild(Module module, Plugin plugin) {
        this.knownModule.put(new NamespacedKey(plugin, module.getName()).toString(), module);
    }

    /**
     * 获取子 Module
     *
     * @param name ModuleChild name
     * @return ModuleChild
     */
    @Override
    public Module getModuleChild(String name) {
        name = name.toLowerCase(Locale.ROOT);
        if (this.name.contains(":"))
            return this.knownModule.get(name);

        for (String s : this.knownModule.keySet()) {
            if (!s.endsWith(":" + name))
                continue;

            return this.knownModule.get(s);
        }
        return null;
    }

    /**
     * 以字符串方式获取所有 ModuleChild
     *
     * @return String
     */
    @Override
    public String getModuleChildrenAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : this.knownModule.keySet()) {
            if (stringBuilder.length() != 0 && !StringUtil.isEmpty(stringBuilder.toString()))
                stringBuilder.append(",");

            stringBuilder.append(this.knownModule.get(s).toString());
        }
        return null;
    }

    /**
     * 卸载 ModuleChild
     *
     * @param name ModuleChild name
     */
    @Override
    public void unregisterModuleChild(String name) {
        this.knownModule.remove(name);
    }

    /**
     * 卸载所有 ModuleChild
     */
    @Override
    public void unregisterModuleChildren() {
        this.knownModule.clear();
    }

    @Override
    public TokenManager getTokenManager() {
        return this.tokenManager;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.name);
        // name
        // name~owner
        // name~save~over
        // name~owner~save~over
        if (this.owner != null)
            stringBuilder.append("~").append(this.owner.getUUID());
        if (this.save != null && this.over != null)
            stringBuilder.append("~").append(DateUtil.dateToString(this.save, FileConfigConstant.SETTING_DATEFORMAT)).append("~").append(DateUtil.dateToString(this.over, FileConfigConstant.SETTING_DATEFORMAT));

        return stringBuilder.toString();
    }

    public String getTable() {
        if (this.parent == null)
            return new VariableFactory(ConstantCommon.TOKEN_TABLE)
                    .function(this.getName())
                    .object("default")
                    .toString();

        return new VariableFactory(ConstantCommon.TOKEN_TABLE)
                .function(this.getParent().getName())
                .object(this.getName())
                .toString();
    }
}