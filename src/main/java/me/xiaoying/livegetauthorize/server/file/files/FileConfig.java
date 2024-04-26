package me.xiaoying.livegetauthorize.server.file.files;

import me.xiaoying.livegetauthorize.core.configuration.YamlConfiguration;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.file.SubFile;

import java.io.File;

/**
 * File Config.yml
 */
public class FileConfig extends SubFile {
    private final File config = new File("Config.yml");

    @Override
    public void newFile() {
        if (!this.config.exists()) this.saveResource(this.config.getName(), false);
        this.setConfiguration(YamlConfiguration.loadConfiguration(this.config));
    }

    @Override
    public void delFile() {
        this.config.delete();
    }

    @Override
    public void initFile() {
        FileConfigConstant.SERVER_HOST = this.getString(FileConfigConstant.SERVER_HOST, "Server.Host");
        FileConfigConstant.SERVER_PORT = this.getInt(FileConfigConstant.SERVER_PORT, "Server.Port");

        FileConfigConstant.SETTING_PASSWORD_ENCRYPT = this.getString(FileConfigConstant.SETTING_PASSWORD_ENCRYPT, "Setting.Password.Encrypt");
        FileConfigConstant.SETTING_PASSWORD_PASSWORD = this.getString(FileConfigConstant.SETTING_PASSWORD_PASSWORD, "Setting.Password.Password");

        FileConfigConstant.SETTING_DATA_CACHE_TIME = this.getInt(FileConfigConstant.SETTING_DATA_CACHE_TIME, "Setting.DateCacheTime");

        FileConfigConstant.SETTING_DATEFORMAT = this.getString(FileConfigConstant.SETTING_DATEFORMAT, "Setting.DateFormat");
        FileConfigConstant.SETTING_DATA_TYPE = this.getString(FileConfigConstant.SETTING_DATA_TYPE, "Setting.Data.Type");
        FileConfigConstant.SETTING_DATA_MYSQL_HOSTNAME = this.getString(FileConfigConstant.SETTING_DATA_MYSQL_HOSTNAME, "Setting.Data.Mysql.Hostname");
        FileConfigConstant.SETTING_DATA_MYSQL_PORT = this.getInt(FileConfigConstant.SETTING_DATA_MYSQL_PORT, "Setting.Data.Mysql.Port");
        FileConfigConstant.SETTING_DATA_MYSQL_DATABASE = this.getString(FileConfigConstant.SETTING_DATA_MYSQL_DATABASE, "Setting.Data.Mysql.Database");
        FileConfigConstant.SETTING_DATA_MYSQL_USERNAME = this.getString(FileConfigConstant.SETTING_DATA_MYSQL_USERNAME, "Setting.Data.Mysql.Username");
        FileConfigConstant.SETTING_DATA_MYSQL_PASSWORD = this.getString(FileConfigConstant.SETTING_DATA_MYSQL_PASSWORD, "Setting.Data.Mysql.Password");
        FileConfigConstant.SETTING_DATA_MYSQL_RECONNECT_TIME = this.getInt(FileConfigConstant.SETTING_DATA_MYSQL_RECONNECT_TIME, "Setting.Data.Mysql.ReconnectTime");
        FileConfigConstant.SETTING_DATA_MYSQL_RECONNECT_DELAY = this.getInt(FileConfigConstant.SETTING_DATA_MYSQL_RECONNECT_DELAY, "Setting.Data.Mysql.ReconnectDelay");

        FileConfigConstant.SETTING_JWT_KET_ID = this.getString(FileConfigConstant.SETTING_JWT_KET_ID, "Setting.Jwt.KeyId");
        FileConfigConstant.SETTING_JWT_PUBLIC_KEY = this.getString(FileConfigConstant.SETTING_JWT_PUBLIC_KEY, "Setting.Jwt.PublicKey");
        FileConfigConstant.SETTING_JWT_PRIVATE_KEY = this.getString(FileConfigConstant.SETTING_JWT_PRIVATE_KEY, "Setting.Jwt.PrivateKey");

        FileConfigConstant.SETTING_DATA_SQLITE_PATH = this.getString(FileConfigConstant.SETTING_DATA_SQLITE_PATH, "Setting.Data.Sqlite.Path");
        FileConfigConstant.SETTING_DATA_SQLITE_FILE = this.getString(FileConfigConstant.SETTING_DATA_SQLITE_FILE, "Setting.Data.Sqlite.File");
    }
}