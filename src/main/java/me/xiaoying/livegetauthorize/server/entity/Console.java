package me.xiaoying.livegetauthorize.server.entity;

import me.xiaoying.livegetauthorize.core.LACore;
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
    public void sendMessage(String message) {
        LACore.getLogger().info(message);
    }

    @Override
    public boolean isPermissionSet(String permission) {
        return true;
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        return true;
    }

    @Override
    public boolean hasPermission(String s) {
        return true;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return true;
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
        return true;
    }

    @Override
    public void setOp(boolean value) {
    }
}