package me.xiaoying.livegetauthorize.server.file.files;

import me.xiaoying.livegetauthorize.core.configuration.YamlConfiguration;
import me.xiaoying.livegetauthorize.server.constant.FileMessageConstant;
import me.xiaoying.livegetauthorize.server.file.SubFile;

import java.io.File;

/**
 * File Message
 */
public class FileMessage extends SubFile {
    private File message = new File("Message.yml");

    @Override
    public void newFile() {
        if (this.message.exists()) saveResource(this.message.getName(), false);
        this.setConfiguration(YamlConfiguration.loadConfiguration(this.message));
    }

    @Override
    public void delFile() {
        this.message.delete();
    }

    @Override
    public void initFile() {
        FileMessageConstant.MESSAGE_ACCOUNT_LOGIN = this.getString(FileMessageConstant.MESSAGE_ACCOUNT_LOGIN, "Account.Login");
        FileMessageConstant.MESSAGE_ACCOUNT_PASSWORD_INVALID = this.getString(FileMessageConstant.MESSAGE_ACCOUNT_PASSWORD_INVALID, "Account.PasswordInvalid");
        FileMessageConstant.MESSAGE_ACCOUNT_NOT_FOUND = this.getString(FileMessageConstant.MESSAGE_ACCOUNT_NOT_FOUND, "Account.NotFound");
    }
}