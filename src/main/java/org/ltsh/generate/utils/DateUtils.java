package org.ltsh.generate.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fengjianbo on 2017/12/28.
 */
public class DateUtils {
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
    public static String format(Date date) {
        return dateFormat.format(date);
    }
}
