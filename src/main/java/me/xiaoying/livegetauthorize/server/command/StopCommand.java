package me.xiaoying.livegetauthorize.server.command;

import me.xiaoying.livegetauthorize.core.command.Command;
import me.xiaoying.livegetauthorize.core.command.CommandSender;

/**
 * Command Stop
 */
public class StopCommand extends Command {
    public StopCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        System.exit(0);
    }
}