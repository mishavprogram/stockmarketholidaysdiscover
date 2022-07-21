package com.ua.mvp.stockmarketholidaysdiscover.controller;

import com.ua.mvp.stockmarketholidaysdiscover.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v0.2")
public class MarketHolidaysController {

    @Autowired
    private QuoteService quoteService;

    @GetMapping("/market-holidays")
    public List<LocalDate> getMarketHolidays() {
        return quoteService.getMarketHolidays();
    }

}
