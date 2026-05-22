package com.example.b_todolist.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    private DateUtils() {
    }

    public static String formatDate(long dueDateMillis) {
        return formatDate(dueDateMillis, "Kein Datum");
    }

    public static String formatDate(long dueDateMillis, String emptyDateText) {
        if (dueDateMillis <= 0L) {
            return emptyDateText;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN, Locale.GERMANY);
        return formatter.format(new Date(dueDateMillis));
    }
}
