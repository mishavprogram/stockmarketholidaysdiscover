package com.ua.mvp.stockmarketholidaysdiscover.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.ua.mvp.stockmarketholidaysdiscover.converter.csv.BigDecimalBeanFieldConverter;
import com.ua.mvp.stockmarketholidaysdiscover.converter.csv.LocalDateBeanFieldConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class Quote {
    @CsvCustomBindByName(column = "Date", converter = LocalDateBeanFieldConverter.class)
    private LocalDate date;
    @CsvCustomBindByName(column = "Open", converter = BigDecimalBeanFieldConverter.class)
    private BigDecimal open;
    @CsvCustomBindByName(column = "High", converter = BigDecimalBeanFieldConverter.class)
    private BigDecimal high;
    @CsvCustomBindByName(column = "Low", converter = BigDecimalBeanFieldConverter.class)
    private BigDecimal low;
    @CsvCustomBindByName(column = "Close", converter = BigDecimalBeanFieldConverter.class)
    private BigDecimal close;
    @CsvCustomBindByName(column = "Adj Close", converter = BigDecimalBeanFieldConverter.class)
    private BigDecimal adjClose;
    @CsvBindByName(column = "Volume")
    private Integer volume;
}
