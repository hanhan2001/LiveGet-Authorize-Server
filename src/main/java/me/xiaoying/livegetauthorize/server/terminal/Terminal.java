package me.xiaoying.livegetauthorize.server.terminal;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.server.entity.Console;

import java.util.Scanner;

/**
 * Terminal
 */
public class Terminal {
    public void start() {
        new Thread(() -> {
            LACore.getLogger().info("For help, type \"help\" or \"?\"");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String scan = scanner.nextLine();

                boolean result = LACore.getServer().getCommandManager().dispatch(new Console(), scan);
                if (result)
                    continue;

                LACore.getLogger().info("Unknown command. Type \"/help\" for help.");
            }
        }).start();
    }
}