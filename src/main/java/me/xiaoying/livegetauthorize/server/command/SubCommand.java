package me.xiaoying.livegetauthorize.server.command;

import me.xiaoying.livegetauthorize.core.command.CommandSender;

/**
 * Command
 */
public abstract class SubCommand {
    public abstract void registerCommand(SubCommand command);

    public abstract void performCommand(CommandSender sender, String[] args);

    public String getDescription() {
        return "null";
    }
}