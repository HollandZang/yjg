package com.holland.holland.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static String getDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(new Date());
    }

    public static String getDateStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(date);
    }

    public static LocalDateTime getDate(String yyyyMMddHHmmss) {
        return LocalDateTime.parse(yyyyMMddHHmmss, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
