package me.xiaoying.livegetauthorize.server.command.module;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.command.Command;
import me.xiaoying.livegetauthorize.core.command.CommandSender;
import me.xiaoying.livegetauthorize.server.command.RegisteredCommand;
import me.xiaoying.livegetauthorize.server.command.SubCommand;

import java.util.*;

/**
 * Command Module
 */
public class ModuleCommand extends Command {
    private final Map<String, List<RegisteredCommand>> registeredCommands = new HashMap<>();

    public ModuleCommand(String name) {
        super(name);
        this.registerCommand(new ModuleListCommand());
        this.registerCommand(new ModuleChildCommand());
    }

    private void registerCommand(SubCommand subCommand) {
        me.xiaoying.livegetauthorize.server.command.Command command = subCommand.getClass().getAnnotation(me.xiaoying.livegetauthorize.server.command.Command.class);

        if (command == null) {
            LACore.getLogger().warn("&eChecked some command(" + subCommand.getClass().getName() + ") don't use Command annotation, please check your code!");
            return;
        }

        for (String s : command.values()) {
            List<RegisteredCommand> list = new ArrayList<>();
            for (int i : command.length())
                list.add(new RegisteredCommand(i, subCommand));

            this.registeredCommands.put(s, list);
        }
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args == null || args.length == 0) {
            this.sendHelpMessage();
            return;
        }

        // 判断是否存在相应命令
        String head = args[0];
        if (!this.registeredCommands.containsKey(head)) {
            this.sendHelpMessage();
            return;
        }

        // 移除 head
        List<String> list = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
        args = list.toArray(new String[0]);

        boolean isDo = false;
        for (RegisteredCommand registeredCommand : this.registeredCommands.get(head)) {
            if (registeredCommand.getLength() != args.length)
                continue;

            registeredCommand.getSubCommand().performCommand(sender, args);
            isDo = true;
        }

        if (isDo) return;

        // 未执行则发出帮助信息
        this.sendHelpMessage();
    }

    private void sendHelpMessage() {
        for (String s : this.registeredCommands.keySet()) {
            RegisteredCommand registeredCommand = this.registeredCommands.get(s).get(0);
            LACore.getLogger().info("module {} -> {}", s, registeredCommand.getSubCommand().getDescription());
        }
    }
}