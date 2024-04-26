package me.xiaoying.livegetauthorize.server.command;

import me.xiaoying.livegetauthorize.core.command.Command;
import me.xiaoying.livegetauthorize.core.command.CommandSender;
import me.xiaoying.livegetauthorize.server.Application;

import java.util.List;

public class ReloadCommand extends Command {
    public ReloadCommand(String name, String description, String usage, List<String> alias) {
        super(name, description, usage, alias);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        Application.unInitialize();
        Application.initialize();
    }
}