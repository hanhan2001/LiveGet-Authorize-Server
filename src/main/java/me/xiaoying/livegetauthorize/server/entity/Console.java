package me.xiaoying.livegetauthorize.server.entity;

import me.xiaoying.livegetauthorize.core.command.CommandSender;
import me.xiaoying.livegetauthorize.core.permission.Permissible;
import me.xiaoying.livegetauthorize.core.permission.PermissibleBase;
import me.xiaoying.livegetauthorize.core.permission.Permission;
import me.xiaoying.livegetauthorize.core.permission.PermissionAttachment;
import me.xiaoying.livegetauthorize.core.plugin.Plugin;

/**
 * Entity CommandSender Console
 */
public class Console implements CommandSender {
    private final Permissible permissible = new PermissibleBase(this);

    @Override
    public String getName() {
        return "Console";
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
    public boolean hasPermission(String s) {
        return this.permissible.hasPermission(s);
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

    }

    @Override
    public boolean isOp() {
        return this.permissible.isOp();
    }

    @Override
    public void setOp(boolean value) {
        this.permissible.setOp(value);
    }
}