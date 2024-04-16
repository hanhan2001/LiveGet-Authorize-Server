package me.xiaoying.livegetauthorize.server;

import me.xiaoying.livegetauthorize.core.command.CommandManager;
import me.xiaoying.livegetauthorize.core.command.SimpleCommandManager;
import me.xiaoying.livegetauthorize.core.plugin.PluginManager;
import me.xiaoying.livegetauthorize.core.plugin.SimplePluginManager;
import me.xiaoying.livegetauthorize.core.server.Server;
import me.xiaoying.logger.LoggerFactory;

import java.io.File;

/**
 * Server Authorize
 */
public class AuthorizeServer implements Server {
    private final PluginManager pluginManager = new SimplePluginManager(this);
    private final CommandManager commandManager = new SimpleCommandManager();

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
    public void run() {
        File file = new File("./logs/latest.log");
        if (!file.exists())
            return;

        new LoggerFactory().saveLog();
    }

    @Override
    public void stop() {
        new LoggerFactory().saveLog();
    }
}