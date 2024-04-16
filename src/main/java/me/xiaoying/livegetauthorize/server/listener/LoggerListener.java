package me.xiaoying.livegetauthorize.server.listener;

import me.xiaoying.logger.event.EventHandler;
import me.xiaoying.logger.event.Listener;
import me.xiaoying.logger.event.terminal.TerminalLogEndEvent;
import me.xiaoying.logger.event.terminal.TerminalWantLogEvent;

/**
 * Listener Logger
 */
public class LoggerListener implements Listener {
    @EventHandler
    public void onLoggerWantLog(TerminalWantLogEvent event) {
        System.out.println("\r");
    }

    @EventHandler
    public void onLoggerLogEnd(TerminalLogEndEvent event) {
        System.out.print("> ");
    }
}