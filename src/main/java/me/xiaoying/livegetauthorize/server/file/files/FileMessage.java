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
        if (!this.message.exists()) saveResource(this.message.getName(), false);
        this.setConfiguration(YamlConfiguration.loadConfiguration(this.message));
    }

    @Override
    public void delFile() {
        this.message.delete();
    }

    @Override
    public void initFile() {
        // 账户信息
        FileMessageConstant.MESSAGE_ACCOUNT_LOGIN = this.getString(FileMessageConstant.MESSAGE_ACCOUNT_LOGIN, "Account.Login");
        FileMessageConstant.MESSAGE_ACCOUNT_INFO = this.getString(FileMessageConstant.MESSAGE_ACCOUNT_INFO, "Account.Info");
        FileMessageConstant.MESSAGE_ACCOUNT_PASSWORD_INVALID = this.getString(FileMessageConstant.MESSAGE_ACCOUNT_PASSWORD_INVALID, "Account.PasswordInvalid");
        FileMessageConstant.MESSAGE_ACCOUNT_NOT_FOUND = this.getString(FileMessageConstant.MESSAGE_ACCOUNT_NOT_FOUND, "Account.NotFound");
        FileMessageConstant.MESSAGE_ACCOUNT_USER_EXISTED = this.getString(FileMessageConstant.MESSAGE_ACCOUNT_USER_EXISTED, "Account.UserExisted");
        FileMessageConstant.MESSAGE_ACCOUNT_EMAIL_EXISTED = this.getString(FileMessageConstant.MESSAGE_ACCOUNT_EMAIL_EXISTED, "Account.EmailExisted");
        FileMessageConstant.MESSAGE_ACCOUNT_NEED_LOGIN = this.getString(FileMessageConstant.MESSAGE_ACCOUNT_NEED_LOGIN, "Account.NeedLogin");

        // 显示界面
        FileMessageConstant.MESSAGE_DISPLAY_PREVIEW_TITLE = this.getString(FileMessageConstant.MESSAGE_DISPLAY_PREVIEW_TITLE, "Display.Preview.Title");

        // 权限信息
        FileMessageConstant.MESSAGE_PERMISSION_NO_PERMISSION = this.getString(FileMessageConstant.MESSAGE_PERMISSION_NO_PERMISSION, "Permission.NoPermission");

        // 错误信息
        FileMessageConstant.ERROR_NORMAL = this.getString(FileMessageConstant.ERROR_NORMAL, "Error.Normal");
    }
}