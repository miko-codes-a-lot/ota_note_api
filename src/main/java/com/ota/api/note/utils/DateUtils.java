package com.ota.api.note.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtils {
    public static String toISOString(Date date) {
        return date.toInstant().toString();
    }

    public static Date toISODate(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return Date.from(dateTime.toInstant(ZoneOffset.UTC));
    }
}
