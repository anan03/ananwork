package com.lvshandian.asktoask.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/7.
 */
public class DateUtil {

    /**
     * 把一个Long类型的数值转换成 指定格式的日期字符串
     *
     * @param time   String类型 long格式的字符串 类似 "111111111111";
     * @param format 格式化的字符串
     * @return 指定格式的字符串 2104年11月11日
     */
    public static String getFormatString(String time, String format) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTimeInMillis(Long.parseLong(time));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            c.setTimeInMillis(0L);
        }
        Date tempDate = c.getTime();
        SimpleDateFormat sdformat = new SimpleDateFormat(format);
        return sdformat.format(tempDate);
    }

    /**
     * 时间转换
     *
     * @param time
     * @return
     */
    public static String timesTwo(long time) {
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(date);
    }

    /**
     * 时间转换
     *
     * @param time
     * @return
     */
    public static String timesOne(long time) {
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    /**
     * 时间转换
     *
     * @param time
     * @return
     */
    public static String timesSwitch(long time) {
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 时间差
     *
     * @param start
     * @param cur
     * @return1.参数一：传入更新时间：参数二：当前时间；
     * @date 2016年6月28日 下午6:33:35
     */
    public static String getRecentlyDays(long start) {

        long cur = System.currentTimeMillis();
        long span = (cur - start) / 1000;
        String Stringtime = "";
        if (span < 60) {
            Stringtime = span + "秒前更新";
            return Stringtime;
        }

        span = span / 60;
        if (span < 60) {

            Stringtime = span + "分钟前更新";
            return Stringtime;
        }

        span = span / 60;
        if (span < 24) {

            Stringtime = span + "小时前更新";
            return Stringtime;
        }
        span = span / 24;
        if (span < 30) {

            Stringtime = span + "天前更新";
            return Stringtime;
        }
        span = span / 30;
        if (span < 12) {

            Stringtime = span + "月前更新";
            return Stringtime;
        }
        span = span / 12;

        Stringtime = span + "年前更新";
        return Stringtime;

    }

}
