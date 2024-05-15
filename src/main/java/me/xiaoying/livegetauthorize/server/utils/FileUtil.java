package me.xiaoying.livegetauthorize.server.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * Util Image
 */
public class FileUtil {
    /**
     * 文件 转 Base64
     *
     * @param filePath 文件
     * @return String
     */
    public static String fileToBase64(String filePath) {
        try {
            return fileToBase64(new FileInputStream(filePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String fileToBase64(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            return new String(Base64.getEncoder().encode(bytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}