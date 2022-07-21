package com.ua.mvp.stockmarketholidaysdiscover.service;

import java.time.LocalDate;
import java.util.List;

public interface QuoteService {
    public List<LocalDate> getMarketHolidays();
}
