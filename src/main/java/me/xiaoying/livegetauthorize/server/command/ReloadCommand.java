package me.xiaoying.livegetauthorize.server.command;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.command.Command;
import me.xiaoying.livegetauthorize.core.command.CommandSender;
import me.xiaoying.livegetauthorize.core.command.commands.HelpCommand;
import me.xiaoying.livegetauthorize.core.message.MessageManager;
import me.xiaoying.livegetauthorize.core.plugin.JavaPluginLoader;
import me.xiaoying.livegetauthorize.core.plugin.Plugin;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.message.SelfInfoMessageExecutor;
import me.xiaoying.livegetauthorize.server.message.UserQuitMessageExecutor;
import me.xiaoying.livegetauthorize.server.message.user.UserOpenClassificationMessageExecutor;
import me.xiaoying.livegetauthorize.server.message.user.UserOpenDisplayMessageExecutor;
import me.xiaoying.livegetauthorize.server.option.SimpleOptionManager;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {
    public ReloadCommand(String name, String description, String usage, List<String> alias) {
        super(name, description, usage, alias);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        Application.unInitialize();

        // plugin
        LACore.getLogger().info("Loading plugins...");
        File plugins = new File("./plugins");
        if (!plugins.exists()) plugins.mkdirs();
        LACore.getServer().getPluginManager().registerInterface(JavaPluginLoader.class);
        LACore.getServer().getPluginManager().loadPlugins(plugins);
        for (Plugin plugin : LACore.getServer().getPluginManager().getPlugins())
            LACore.getServer().getPluginManager().enablePlugin(plugin);

        // MessageExecutor
        MessageManager messageManager = LACore.getServer().getMessageManager();
        messageManager.registerMessageExecutor("user_quit", new UserQuitMessageExecutor());
        messageManager.registerMessageExecutor("user_info", new SelfInfoMessageExecutor());
        messageManager.registerMessageExecutor("open_display", new UserOpenDisplayMessageExecutor());
        messageManager.registerMessageExecutor("open_classification", new UserOpenClassificationMessageExecutor());

        // OptionManager
        SimpleOptionManager optionManager = (SimpleOptionManager) LACore.getServer().getOptionManager();
        optionManager.initialize();

        // register commands
        LACore.getServer().getCommandManager().registerCommand("stop", new StopCommand("stop"));
        LACore.getServer().getCommandManager().registerCommand("plugins", new PluginCommand("plugins", "Get server loaded plugins", "/pl", Collections.singletonList("pl")));
        LACore.getServer().getCommandManager().registerCommand("reload", new ReloadCommand("reload", "Reload server", "/reload", null));
        LACore.getServer().getCommandManager().registerCommand("jwt", new JwtCommand("jwt", "create new jwt", "/jwt", null));
        LACore.getServer().getCommandManager().registerCommand("authorizecore", new HelpCommand("help"));
    }
}