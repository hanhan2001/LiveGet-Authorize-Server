package me.xiaoying.livegetauthorize.server.entity;

import me.xiaoying.livegetauthorize.core.command.CommandSender;

/**
 * Entity CommandSender Console
 */
public class Console implements CommandSender {
    @Override
    public boolean hasPermission(String s) {
        return true;
    }
}