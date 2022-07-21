package com.ua.mvp.stockmarketholidaysdiscover.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class DateUtilsTest {

    @Test
    public void whenDateReturnIsWeekend() {
        //WHEN
        LocalDate friday = LocalDate.of(2022, 7, 1);
        LocalDate saturday = LocalDate.of(2022, 7, 2);
        LocalDate sunday = LocalDate.of(2022, 7, 3);
        LocalDate monday = LocalDate.of(2022, 7, 4);

        //THEN
        Assertions.assertFalse(DateUtils.isWeekend(friday));
        Assertions.assertTrue(DateUtils.isWeekend(saturday));
        Assertions.assertTrue(DateUtils.isWeekend(sunday));
        Assertions.assertFalse(DateUtils.isWeekend(monday));
    }

}