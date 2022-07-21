package com.ua.mvp.stockmarketholidaysdiscover.service.impl;

import com.ua.mvp.stockmarketholidaysdiscover.data.QuoteRowsProvider;
import com.ua.mvp.stockmarketholidaysdiscover.model.Quote;
import com.ua.mvp.stockmarketholidaysdiscover.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Component
public class QuoteServiceImpl implements QuoteService {
    @Autowired
    private QuoteRowsProvider quoteRowsProvider;

    @Override
    public List<LocalDate> getMarketHolidays() {
        List<Quote> allRows = quoteRowsProvider.getAllRows();

        if (allRows.isEmpty()) {
            return Collections.emptyList();
        }

        List<LocalDate> dates = allRows
                .stream()
                .map(Quote::getDate)
                .toList();

        if (dates.isEmpty()) {
            return Collections.emptyList();
        }

        LocalDate end = dates.stream().max(LocalDate::compareTo).get();
        LocalDate start = dates.stream().min(LocalDate::compareTo).get();

        return Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .filter(x -> !isWeekend(x))
                .filter(x -> !dates.contains(x))
                .toList();
    }

    public static boolean isWeekend(LocalDate localDate) {
        DayOfWeek day = localDate.getDayOfWeek();
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }
}
