package me.xiaoying.livegetauthorize.server;

import me.xiaoying.livegetauthorize.core.command.CommandManager;
import me.xiaoying.livegetauthorize.core.command.SimpleCommandManager;
import me.xiaoying.livegetauthorize.core.permission.PermissionManager;
import me.xiaoying.livegetauthorize.core.plugin.PluginManager;
import me.xiaoying.livegetauthorize.core.plugin.SimplePluginManager;
import me.xiaoying.livegetauthorize.core.scheduler.Scheduler;
import me.xiaoying.livegetauthorize.core.server.Server;
import me.xiaoying.livegetauthorize.server.permission.SimplePermissionManager;
import me.xiaoying.livegetauthorize.server.scheduler.ServerScheduler;

/**
 * Server Authorize
 */
public class AuthorizeServer implements Server {
    private final PluginManager pluginManager = new SimplePluginManager(this);
    private final CommandManager commandManager = new SimpleCommandManager();
    private final Scheduler scheduler = new ServerScheduler();
    private PermissionManager permissionManager = new SimplePermissionManager();

    @Override
    public String getName() {
        return "Authorize";
    }

    @Override
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public PermissionManager getPermissionManager() {
        return this.permissionManager;
    }

    @Override
    public void setPermissionManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }

    @Override
    public void run() {
    }

    @Override
    public void stop() {
    }
}