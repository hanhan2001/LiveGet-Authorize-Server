package me.xiaoying.livegetauthorize.server.utils;

import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;

/**
 * Util Server
 */
public class ServerUtil {
    public static String getEncryptPassword(String password) {
        switch (FileConfigConstant.SETTING_PASSWORD_ENCRYPT.toUpperCase()) {
            case "SHA256":
                password = EncryptUtil.SHA256Encrypt(password);
                break;
            case "MD5":
                password = EncryptUtil.md5Encrypt(password);
                break;
            case "BASE64":
                password = EncryptUtil.base64Encrypt(password);
                break;
        }
        return password;
    }
}