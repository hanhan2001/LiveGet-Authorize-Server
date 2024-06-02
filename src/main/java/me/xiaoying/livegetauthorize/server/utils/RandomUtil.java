package me.xiaoying.livegetauthorize.server.utils;

import java.util.Random;

/**
 * 工具类 随机数
 */
public class RandomUtil {
    /**
     * 获取固定长度的随机字符串
     *
     * @param length 长度
     * @return String
     */
    public static String getCharAndNumr(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (choice + random.nextInt(26)));
            } else { // 数字
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }
}