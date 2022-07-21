package com.ua.mvp.stockmarketholidaysdiscover.converter.csv;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateBeanFieldConverter extends AbstractBeanField<String> {
    @Override
    protected Object convert(String value) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
