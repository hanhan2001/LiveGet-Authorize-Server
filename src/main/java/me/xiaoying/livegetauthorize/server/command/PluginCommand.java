package me.xiaoying.livegetauthorize.server.command;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.command.Command;
import me.xiaoying.livegetauthorize.core.command.CommandSender;
import me.xiaoying.livegetauthorize.core.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class PluginCommand extends Command {
    public PluginCommand(String name, String description, String usage, List<String> alias) {
        super(name, description, usage, alias);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("server.admin") && !sender.isOp())
            return;

        for (Plugin plugin : LACore.getServer().getPluginManager().getPlugins())
            LACore.getLogger().info(plugin.getDescription().getName() + "({}) - {}", plugin.getDescription().getVersion(), Arrays.toString(plugin.getDescription().getAuthors()));
    }
}