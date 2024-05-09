package me.xiaoying.livegetauthorize.server;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.plugin.JavaPluginLoader;
import me.xiaoying.livegetauthorize.core.plugin.Plugin;
import me.xiaoying.livegetauthorize.core.server.Server;
import me.xiaoying.livegetauthorize.server.command.JwtCommand;
import me.xiaoying.livegetauthorize.server.command.PluginCommand;
import me.xiaoying.livegetauthorize.server.command.ReloadCommand;
import me.xiaoying.livegetauthorize.server.command.StopCommand;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.file.FileService;
import me.xiaoying.livegetauthorize.server.file.files.FileConfig;
import me.xiaoying.livegetauthorize.server.file.files.FileMessage;
import me.xiaoying.livegetauthorize.server.listener.LoggerListener;
import me.xiaoying.livegetauthorize.server.terminal.Terminal;
import me.xiaoying.livegetauthorize.server.user.UserManager;
import me.xiaoying.logger.event.EventHandle;
import me.xiaoying.sql.MysqlFactory;
import me.xiaoying.sql.SqlFactory;
import me.xiaoying.sql.SqliteFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

/**
 * Application
 */
@SpringBootApplication
public class Application {
    private static Server server;
    private static Terminal terminal;
    private static FileService fileService;
    private static UserManager userManager;

    public static void main(String[] args) {
        LACore.getLogger().info("Starting server...");
        initialize();


        System.getProperties().setProperty("server.address", FileConfigConstant.SERVER_HOST);
        System.getProperties().setProperty("server.port", String.valueOf(FileConfigConstant.SERVER_PORT));
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setLogStartupInfo(false);
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

        // FileService
        fileService = new FileService();
        fileService.register("Config", new FileConfig());
        fileService.register("Message", new FileMessage());
        fileService.fileAll();
        fileService.initAll();

        // Server
        server = new AuthorizeServer();
        server.run();
        LACore.setServer(server);

        // UserService
        userManager = new UserManager();

        // plugin
        LACore.getLogger().info("Loading plugins...");
        File plugins = new File("./plugins");
        if (!plugins.exists()) plugins.mkdirs();
        server.getPluginManager().registerInterface(JavaPluginLoader.class);
        server.getPluginManager().loadPlugins(plugins);
        for (Plugin plugin : server.getPluginManager().getPlugins())
            server.getPluginManager().enablePlugin(plugin);

        // register commands
        server.getCommandManager().registerCommand("stop", new StopCommand("stop"));
        server.getCommandManager().registerCommand("plugins", new PluginCommand("plugins", "Get server loaded plugins", "/pl", Collections.singletonList("pl")));
        server.getCommandManager().registerCommand("reload", new ReloadCommand("reload", "Reload server", "/reload", null));
        server.getCommandManager().registerCommand("jwt", new JwtCommand("jwt", "create new jwt", "/jwt", null));
    }

    // 取消初始化
    public static void unInitialize() {
        LACore.getLogger().info("Disable plugins...");
        server.getPluginManager().disablePlugins();

        LACore.getLogger().info("Remove Server...");
    }

    public static Server getServer() {
        return server;
    }

    public static FileService getFileService() {
        return fileService;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public static SqlFactory getSqlFactory() {
        switch (FileConfigConstant.SETTING_DATA_TYPE.toUpperCase()) {
            case "MYSQL":
                return new MysqlFactory(FileConfigConstant.SETTING_DATA_MYSQL_HOSTNAME, FileConfigConstant.SETTING_DATA_MYSQL_PORT, FileConfigConstant.SETTING_DATA_MYSQL_DATABASE, FileConfigConstant.SETTING_DATA_MYSQL_USERNAME, FileConfigConstant.SETTING_DATA_MYSQL_PASSWORD);
            case "SQLITE":
                return new SqliteFactory(FileConfigConstant.SETTING_DATA_SQLITE_PATH, FileConfigConstant.SETTING_DATA_SQLITE_FILE);
        }
        return new SqliteFactory("/", "authorize.db");
    }
}