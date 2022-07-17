package com.zhangll.jmock.core.uitl;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class DateUtil {
    public static Pattern TimePattern  = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
    public static Pattern DatePattern  = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    public static Pattern TimestampPattern  = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
    static SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat TimestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) throws ParseException {
        assert TimePattern.matcher("00:12:13").matches();
        assert TimestampPattern.matcher("2015-12-20 00:12:12").matches();
        assert DatePattern.matcher("2015-12-20").matches();
        System.out.println(TimestampFormat.parse("2015-12-20 00:12:12").getTime());
    }

    public static SimpleDateFormat getFormat(Class cClass) {
        if(cClass == Date.class){
            return DateFormat;
        }else if(cClass == Time.class){
            return TimeFormat;
        }else if(cClass == Timestamp.class){
            return TimestampFormat;
        } else if(cClass == java.util.Date.class){
            return TimestampFormat;
        }
        return null;
    }

    public static boolean isSqlTime(Class cClass){
        return cClass == Date.class
                | cClass == Time.class
                | cClass == Timestamp.class
                | cClass == java.util.Date.class;
    }

    public static long getBase(Class cClass) {
        if(cClass == Date.class){
            // 天
            return 60 * 60 * 24 * 1000;
        } else if (cClass == Time.class) {
            // 秒
            return 1 * 1000;
        } else if (cClass == Timestamp.class) {
            // 秒
            return 1 * 1000;
        }
        return 0;
    }

    public static String initDate(Class cClass){
        if(cClass == Date.class){
            // 天
            return "1970-01-01";
        } else if (cClass == Time.class) {
            // 秒
            return "00:00:00";
        } else if (cClass == Timestamp.class) {
            // 秒
            return "1970-01-01 00:00:00";
        }
        return "0";
    }

}
