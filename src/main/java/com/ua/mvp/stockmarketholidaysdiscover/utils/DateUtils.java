package com.ua.mvp.stockmarketholidaysdiscover.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static boolean isWeekend(LocalDate localDate) {
        DayOfWeek day = localDate.getDayOfWeek();
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }
}
