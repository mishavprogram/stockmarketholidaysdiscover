package com.ua.mvp.stockmarketholidaysdiscover.service;

import com.ua.mvp.stockmarketholidaysdiscover.model.Quote;

import java.util.List;

public interface CSVRowsReader<T> {
    List<Quote> getRows(String path);
}
