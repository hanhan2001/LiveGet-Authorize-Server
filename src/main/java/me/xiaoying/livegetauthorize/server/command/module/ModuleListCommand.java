package me.xiaoying.livegetauthorize.server.command.module;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.command.CommandSender;
import me.xiaoying.livegetauthorize.core.module.Module;
import me.xiaoying.livegetauthorize.server.command.Command;
import me.xiaoying.livegetauthorize.server.command.SubCommand;
import me.xiaoying.livegetauthorize.server.utils.StringUtil;

@Command(values = "list", length = 0)
public class ModuleListCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        for (Module module : LACore.getServer().getModuleManager().getModules()) {
            if (StringUtil.isEmpty(module.getDescription()))
                LACore.getLogger().info(module.getName() + " -> {}", "null");
            else
                LACore.getLogger().info(module.getName() + " -> {}", module.getDescription());
        }
    }

    @Override
    public String getDescription() {
        return "list modules.";
    }
}