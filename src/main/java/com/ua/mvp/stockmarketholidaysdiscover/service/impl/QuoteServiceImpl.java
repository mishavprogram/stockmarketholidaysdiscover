package com.ua.mvp.stockmarketholidaysdiscover.service.impl;

import com.google.common.collect.Comparators;
import com.ua.mvp.stockmarketholidaysdiscover.data.QuoteRowsProvider;
import com.ua.mvp.stockmarketholidaysdiscover.exceptions.CsvRowsOrderException;
import com.ua.mvp.stockmarketholidaysdiscover.exceptions.CsvRowsUniqueException;
import com.ua.mvp.stockmarketholidaysdiscover.exceptions.LowCountOfRowsException;
import com.ua.mvp.stockmarketholidaysdiscover.model.Quote;
import com.ua.mvp.stockmarketholidaysdiscover.service.QuoteService;
import com.ua.mvp.stockmarketholidaysdiscover.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
@AllArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRowsProvider quoteRowsProvider;

    @Override
    public List<LocalDate> getMarketHolidays() {
        List<Quote> allRows = quoteRowsProvider.getAllRows();

        checkThatQuotesAreValid(allRows);

        return getMarketHolidaysForSortedUniqueRows(allRows);
    }

    private List<LocalDate> getMarketHolidaysForSortedUniqueRows(List<Quote> quotes) {
        List<LocalDate> marketHolidays = new LinkedList<>();

        for (int i = 0; i < quotes.size() - 1; i++) {

            LocalDate current = quotes.get(i).getDate();
            LocalDate next = quotes.get(i + 1).getDate();

            while (Period.between(current, next).getDays() > 1) {
                current = current.plusDays(1L);
                if (!DateUtils.isWeekend(current)) {
                    marketHolidays.add(current);
                }
            }
        }

        return marketHolidays;
    }

    private void checkThatQuotesAreValid(List<Quote> allRows) {
        if (allRows.size() < 2) {
            throw new LowCountOfRowsException();
        }

        if (!Comparators.isInOrder(allRows, Comparator.comparing(Quote::getDate))) {
            throw new CsvRowsOrderException();
        }

        Set<Quote> set = new TreeSet<>(Comparator.comparing(Quote::getDate));
        set.addAll(allRows);

        if (set.size() != allRows.size()) {
            throw new CsvRowsUniqueException();
        }
    }
}
