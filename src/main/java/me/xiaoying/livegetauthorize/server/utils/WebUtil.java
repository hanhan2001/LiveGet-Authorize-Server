package me.xiaoying.livegetauthorize.server.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Util web
 */
public class WebUtil {
    /**
     * 获取网页 InputStream
     *
     * @param url 网址
     * @return InputStream
     */
    public static InputStream getInputStream(String url) {
        try {
            URL webUrl = new URL(url);
            HttpURLConnection httpUrlConn = (HttpURLConnection) webUrl.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            return httpUrlConn.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取网页内容
     *
     * @param url 网页地址
     * @return String
     */
    public static String getWebContent(String url) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL webUrl = new URL(url);
            HttpURLConnection httpUrlConn = (HttpURLConnection) webUrl.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //获取输入流
            InputStream input = httpUrlConn.getInputStream();
            //将字节输入流转换为字符输入流
            InputStreamReader read = new InputStreamReader(input, StandardCharsets.UTF_8);
            //为字符输入流添加缓冲
            BufferedReader br = new BufferedReader(read);
            // 读取返回结果
            String data = br.readLine();
            while(data != null)  {
                stringBuilder.append(data).append("\n");
                data = br.readLine();
            }
            // 释放资源
            br.close();
            read.close();
            input.close();
            httpUrlConn.disconnect();
        } catch (Exception e) {
            return null;
        }

        return stringBuilder.toString();
    }
}