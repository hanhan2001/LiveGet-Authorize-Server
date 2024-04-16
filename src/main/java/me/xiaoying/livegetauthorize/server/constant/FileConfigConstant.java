package me.xiaoying.livegetauthorize.server.constant;

/**
 * Constant FileConfig
 */
public class FileConfigConstant {
    public static String SERVER_HOST = "0.0.0.0";
    public static int SERVER_PORT = 22332;

    public static String SETTING_PASSWORD_ENCRYPT = "SHA256",
            SETTING_PASSWORD_PASSWORD = "C7806234B5A8590291323D3A3D9016ED65F33284BD546EB17E99F7BB4EF7D5A3",
            SETTING_DATEFORMAT = "yyyy/MM/dd-HH:mm:ss",
            SETTING_DATA_TYPE = "MYSQL",
            SETTING_DATA_MYSQL_HOSTNAME = "jdbc:mysql://localhost",
            SETTING_DATA_MYSQL_DATABASE = "authorize",
            SETTING_DATA_MYSQL_USERNAME = "root",
            SETTING_DATA_MYSQL_PASSWORD = "root",
            SETTING_DATA_SQLITE_PATH = "/",
            SETTING_DATA_SQLITE_FILE = "authorize.db";

    public static int SETTING_DATA_CACHE_TIME = 600000,
            SETTING_DATA_MYSQL_PORT = 3306,
            SETTING_DATA_MYSQL_RECONNECT_TIME = 10,
            SETTING_DATA_MYSQL_RECONNECT_DELAY = 3;
}