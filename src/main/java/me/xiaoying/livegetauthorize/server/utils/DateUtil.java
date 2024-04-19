package me.xiaoying.livegetauthorize.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util Date
 */
public class DateUtil {
    /**
     * 获取格式日期
     *
     * @param format 格式
     * @return String*Date
     */
    public static String newStringDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }


    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 字符串转 Date
     *
     * @param date 字符串日期
     * @param format 格式
     * @return Date
     */
    public static Date stringToDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期相减
     *
     * @param date 日期 1
     * @param date1 日期 2
     * @return 减后时间
     */
    public static long getDateReduce(Date date, Date date1) {
        return date.getTime() - date1.getTime();
    }

    /**
     * 日期相减
     *
     * @param date 日期 1
     * @param date1 日期 2
     * @param format 格式
     * @return 减后时间
     */
    public static long getDateReduce(String date, String date1, String format) {
        Date d = null;
        Date d1 = null;
        try {
            d = new SimpleDateFormat(format).parse(date);
            d1 = new SimpleDateFormat(format).parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d != null;
        assert d1 != null;
        return d.getTime() - d1.getTime();
    }
}