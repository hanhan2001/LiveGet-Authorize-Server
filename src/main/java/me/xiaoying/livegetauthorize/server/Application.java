package me.xiaoying.livegetauthorize.server;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.server.Server;
import me.xiaoying.livegetauthorize.server.file.FileService;
import me.xiaoying.livegetauthorize.server.file.files.FileConfig;
import me.xiaoying.livegetauthorize.server.listener.LoggerListener;
import me.xiaoying.livegetauthorize.server.terminal.Terminal;
import me.xiaoying.logger.event.EventHandle;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * Application
 */
@SpringBootApplication
public class Application {
    private static Server server;
    private static Terminal terminal;
    private static FileService fileService;

    public static void main(String[] args) {
        LACore.getLogger().info("Starting server...");
        initialize();

        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run();

        // Terminal
        terminal = new Terminal();
        terminal.start();
        EventHandle.registerEvent(new LoggerListener());
    }

    // 初始化
    public static void initialize() {
        LACore.getLogger().info("Initializing...");
        fileService = new FileService();
        fileService.register("Config", new FileConfig());

        // Server
        server = new AuthorizeServer();
        server.run();

        // plugin
        LACore.getLogger().info("Loading plugins...");
        File plugins = new File("./plugins");
        if (!plugins.exists()) plugins.mkdirs();
        server.getPluginManager().loadPlugins(plugins);

        LACore.setServer(server);
    }

    public static Server getServer() {
        return server;
    }

    public static FileService getFileService() {
        return fileService;
    }
}