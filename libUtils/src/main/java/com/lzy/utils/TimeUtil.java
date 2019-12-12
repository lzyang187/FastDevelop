package com.lzy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author: cyli8
 * @date: 2018/3/8 09:18
 */

public class TimeUtil {
    public static String getYMDHms(long t) {
        return getSpecialFormatTime("yyyy-MM-dd HH:mm:ss", t);
    }

    public static String getSpecialFormatTime(String pattern, long time) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        Date currentTime = new Date(time);
        return format.format(currentTime);
    }

    public static long formatTimeToMillis(String timeType, String time) {
        SimpleDateFormat format = new SimpleDateFormat(timeType, Locale.CHINA);
        try {
            Date date = format.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static boolean isCurDate(long lastTime) {
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String sp_time = sf.format(lastTime);
        String current_time = sf.format(timeMillis);
        return sp_time.equals(current_time);
    }

    /**
     * 某时间点和现在时间对比
     */
    public static boolean isFuture(long compareTime) {
        return compareTime > System.currentTimeMillis();
    }
}
