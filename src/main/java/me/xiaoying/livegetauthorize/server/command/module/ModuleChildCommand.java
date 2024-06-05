package me.xiaoying.livegetauthorize.server.command.module;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.command.CommandSender;
import me.xiaoying.livegetauthorize.core.module.Module;
import me.xiaoying.livegetauthorize.server.command.Command;
import me.xiaoying.livegetauthorize.server.command.SubCommand;

@Command(values = "child", length = 1)
public class ModuleChildCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        Module module = LACore.getServer().getModuleManager().getModule(args[0]);
        if (module == null) {
            LACore.getLogger().info("&c未知 Module");
            return;
        }

        System.out.println(module.getModuleChildrenAsString());
    }
}