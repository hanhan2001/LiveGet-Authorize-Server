package me.xiaoying.livegetauthorize.server;

import me.xiaoying.livegetauthorize.core.classification.ClassificationManager;
import me.xiaoying.livegetauthorize.core.command.CommandManager;
import me.xiaoying.livegetauthorize.core.command.SimpleCommandManager;
import me.xiaoying.livegetauthorize.core.message.MessageManager;
import me.xiaoying.livegetauthorize.core.message.SimpleMessageManager;
import me.xiaoying.livegetauthorize.core.plugin.PluginManager;
import me.xiaoying.livegetauthorize.core.plugin.SimplePluginManager;
import me.xiaoying.livegetauthorize.core.scheduler.Scheduler;
import me.xiaoying.livegetauthorize.core.server.Server;
import me.xiaoying.livegetauthorize.server.classification.SimpleClassificationManager;
import me.xiaoying.livegetauthorize.server.scheduler.ServerScheduler;

/**
 * Server Authorize
 */
public class AuthorizeServer implements Server {
    private final PluginManager pluginManager = new SimplePluginManager(this);
    private final CommandManager commandManager = new SimpleCommandManager();
    private final Scheduler scheduler = new ServerScheduler();
    private final ClassificationManager classificationManager = new SimpleClassificationManager();
    private final MessageManager messageManager = new SimpleMessageManager();

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
    public ClassificationManager getClassificationManager() {
        return this.classificationManager;
    }

    @Override
    public MessageManager getMessageManager() {
        return this.messageManager;
    }

    @Override
    public void run() {
    }

    @Override
    public void stop() {
    }
}