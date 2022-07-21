package com.ua.mvp.stockmarketholidaysdiscover.service.impl;

import com.ua.mvp.stockmarketholidaysdiscover.data.QuoteRowsProvider;
import com.ua.mvp.stockmarketholidaysdiscover.exceptions.LowCountOfRowsException;
import com.ua.mvp.stockmarketholidaysdiscover.model.Quote;
import com.ua.mvp.stockmarketholidaysdiscover.service.QuoteService;
import com.ua.mvp.stockmarketholidaysdiscover.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRowsProvider quoteRowsProvider;

    @Override
    public List<LocalDate> getMarketHolidays() {
        List<Quote> allRows = quoteRowsProvider.getAllRows();

        if (allRows.size() < 2) {
            throw new LowCountOfRowsException();
        }

        List<LocalDate> dates = allRows
                .stream()
                .map(Quote::getDate)
                .toList();

        LocalDate end = dates.stream().max(LocalDate::compareTo).get();
        LocalDate start = dates.stream().min(LocalDate::compareTo).get();

        return Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .filter(x -> !DateUtils.isWeekend(x))
                .filter(x -> !dates.contains(x))
                .toList();
    }
}
