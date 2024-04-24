package me.xiaoying.livegetauthorize.server;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.server.Server;
import me.xiaoying.livegetauthorize.server.command.JwtCommand;
import me.xiaoying.livegetauthorize.server.command.StopCommand;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.file.FileService;
import me.xiaoying.livegetauthorize.server.file.files.FileConfig;
import me.xiaoying.livegetauthorize.server.listener.LoggerListener;
import me.xiaoying.livegetauthorize.server.terminal.Terminal;
import me.xiaoying.livegetauthorize.server.user.UserService;
import me.xiaoying.logger.LoggerFactory;
import me.xiaoying.logger.event.EventHandle;
import me.xiaoying.sql.MysqlFactory;
import me.xiaoying.sql.SqlFactory;
import me.xiaoying.sql.SqliteFactory;
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
    private static UserService userService;

    public static void main(String[] args) {
        LACore.getLogger().info("Starting server...");
        initialize();

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
        fileService.fileAll();
        fileService.initAll();

        // UserService
        userService = new UserService();

        // Server
        server = new AuthorizeServer();
        server.run();

        // plugin
        LACore.getLogger().info("Loading plugins...");
        File plugins = new File("./plugins");
        if (!plugins.exists()) plugins.mkdirs();
        server.getPluginManager().loadPlugins(plugins);
        server.getCommandManager().registerCommand("stop", new StopCommand("stop"));
        server.getCommandManager().registerCommand("jwt", new JwtCommand("jwt", "create new jwt", "/jwt", null));

        LACore.setServer(server);
    }

    public static Server getServer() {
        return server;
    }

    public static FileService getFileService() {
        return fileService;
    }

    public static UserService getUserService() {
        return userService;
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