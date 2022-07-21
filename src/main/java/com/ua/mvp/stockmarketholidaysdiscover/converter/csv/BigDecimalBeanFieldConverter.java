package com.ua.mvp.stockmarketholidaysdiscover.converter.csv;

import com.opencsv.bean.AbstractBeanField;

import java.math.BigDecimal;

public class BigDecimalBeanFieldConverter extends AbstractBeanField<Double> {
    @Override
    protected Object convert(String value) {
        return BigDecimal.valueOf(Double.parseDouble(value));
    }
}
