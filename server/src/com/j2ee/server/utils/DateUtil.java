package com.j2ee.server.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by laizhiyuan on 2017/8/30.
 */
public abstract class DateUtil {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);

    public static synchronized SimpleDateFormat getSimpleDateFormat(String format){
        if (format == null || DEFAULT_FORMAT.equals(format)){
            return simpleDateFormat;
        }else {
            return new SimpleDateFormat(format);
        }
    }

    public static Date getCurrentDate(String format){
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(format);
        String currentDateStr = simpleDateFormat.format(new Date());
        try {
            Date currentDate = simpleDateFormat.parse(currentDateStr);
            return currentDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp getCurrentTimestamp(String format){
        return Timestamp.valueOf(getCurrentDateStr(format));
    }

    public static Timestamp string2Timestamp(String time, String format){
        if (time == null || "".equals(time)){
            return null;
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(format);
        Timestamp timestamp = Timestamp.valueOf(simpleDateFormat.format(time));
        return timestamp;
    }

    public static Long stringTime2Unix(String time, String format){
        if (time == null || "".equals(time)){
            return null;
        }
        DateFormat simpleDateFormat = getSimpleDateFormat(format);
        try {
            Date currentDate = simpleDateFormat.parse(time);
            long unix = currentDate.getTime();
            return unix;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentDateStr(String format){
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(format);
        String currentDateStr = simpleDateFormat.format(new Date());
        return currentDateStr;
    }

}
