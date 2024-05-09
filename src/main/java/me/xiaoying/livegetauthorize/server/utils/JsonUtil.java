package me.xiaoying.livegetauthorize.server.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具 JSON
 */
public class JsonUtil {

    /**
     * 是否是 JSON 文本
     *
     * @param string 源文本
     * @return Boolean
     */
    public static boolean isJson(String string) {
        try {
            JSONObject.parseObject(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 从一段文本中查找json字段
     *
     * @param input 源文本
     * @return String
     */
    public static String extractJsonFromString(String input) {
        String jsonPattern = "\\{(?:[^{}]|\\{(?:[^{}]|\\{[^{}]*})*})*}"; // 匹配嵌套的 JSON

        Pattern pattern = Pattern.compile(jsonPattern);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group();
        }

        return null; // 如果未找到匹配的 JSON，则返回 null
    }

    /**
     * 是否是 JSON 文本
     *
     * @param string 源文本
     * @return Boolean
     */
    public static JSONObject parseJson(String string) {
        try {
            return JSONObject.parseObject(string);
        } catch (Exception e) {
            return null;
        }
    }
}