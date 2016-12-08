package com.kalmahik.firstchat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    public static long now() {
        return (Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
    }

    public static String tsToTime(long ts) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return format.format(new Date(ts));
    }

    public static String tsToDate(long ts) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        return format.format(new Date(ts));
    }
}