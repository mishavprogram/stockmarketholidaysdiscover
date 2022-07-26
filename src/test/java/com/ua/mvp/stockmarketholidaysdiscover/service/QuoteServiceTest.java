package com.ua.mvp.stockmarketholidaysdiscover.service;

import com.ua.mvp.stockmarketholidaysdiscover.data.QuoteRowsProvider;
import com.ua.mvp.stockmarketholidaysdiscover.exceptions.CsvRowsOrderException;
import com.ua.mvp.stockmarketholidaysdiscover.exceptions.CsvRowsUniqueException;
import com.ua.mvp.stockmarketholidaysdiscover.exceptions.LowCountOfRowsException;
import com.ua.mvp.stockmarketholidaysdiscover.model.Quote;
import com.ua.mvp.stockmarketholidaysdiscover.service.impl.QuoteRowsReader;
import com.ua.mvp.stockmarketholidaysdiscover.service.impl.QuoteServiceImpl;
import com.ua.mvp.stockmarketholidaysdiscover.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuoteServiceTest {

    @Test
    void whenProviderReturnsEmptyListThrowException() {
        //GIVEN
        QuoteRowsProvider quoteRowsProvider = mock(QuoteRowsProvider.class);
        QuoteService quoteService = new QuoteServiceImpl(quoteRowsProvider);

        when(quoteRowsProvider.getAllRows()).thenReturn(Collections.emptyList());

        //THEN
        Assertions.assertThrows(LowCountOfRowsException.class, quoteService::getMarketHolidays);
    }

    @Test
    void whenProviderReturnsUnorderedListThrowException() {
        //GIVEN
        QuoteRowsProvider quoteRowsProvider = mock(QuoteRowsProvider.class);
        QuoteService quoteService = new QuoteServiceImpl(quoteRowsProvider);

        when(quoteRowsProvider.getAllRows()).thenReturn(getUnorderedQuotes());

        //THEN
        Assertions.assertThrows(CsvRowsOrderException.class, quoteService::getMarketHolidays);
    }

    @Test
    void whenProviderReturnsNotUniqueListThrowException() {
        //GIVEN
        QuoteRowsProvider quoteRowsProvider = mock(QuoteRowsProvider.class);
        QuoteService quoteService = new QuoteServiceImpl(quoteRowsProvider);

        when(quoteRowsProvider.getAllRows()).thenReturn(getNotUniqueQuotes());

        //THEN
        Assertions.assertThrows(CsvRowsUniqueException.class, quoteService::getMarketHolidays);
    }

    @Test
    void whenProviderReturnsWorkingDaysQuotesReturnResult() {
        //GIVEN
        QuoteRowsProvider quoteRowsProvider = mock(QuoteRowsProvider.class);
        QuoteService quoteService = new QuoteServiceImpl(quoteRowsProvider);

        List<Quote> quotes = getQuotesForWorkingDays();

        when(quoteRowsProvider.getAllRows()).thenReturn(quotes);

        //WHEN
        List<LocalDate> marketHolidays = quoteService.getMarketHolidays();

        //THEN
        List<LocalDate> expected = new ArrayList<>();
        expected.add(LocalDate.of(2022, 7, 4));
        expected.add(LocalDate.of(2022, 7, 5));

        Assertions.assertNotNull(marketHolidays);
        Assertions.assertFalse(marketHolidays.isEmpty());
        Assertions.assertEquals(2, marketHolidays.size());
        Assertions.assertEquals(marketHolidays, expected);
    }

    @Test
    void whenProviderReturnsQuotesReturnResult() {
        //GIVEN
        QuoteRowsProvider quoteRowsProvider = mock(QuoteRowsProvider.class);
        QuoteService quoteService = new QuoteServiceImpl(quoteRowsProvider);

        List<Quote> quotes = getQuotes();

        when(quoteRowsProvider.getAllRows()).thenReturn(quotes);

        //WHEN
        List<LocalDate> marketHolidays = quoteService.getMarketHolidays();

        //THEN
        List<LocalDate> expected = new ArrayList<>();
        expected.add(LocalDate.of(2022, 7, 4));
        expected.add(LocalDate.of(2022, 7, 5));

        Assertions.assertNotNull(marketHolidays);
        Assertions.assertFalse(marketHolidays.isEmpty());
        Assertions.assertEquals(2, marketHolidays.size());
        Assertions.assertEquals(marketHolidays, expected);
    }

    @Test
    void whenProviderReturnsQuotesReturnResult_testSuite2() {
        //GIVEN
        QuoteRowsProvider quoteRowsProvider = mock(QuoteRowsProvider.class);
        QuoteService quoteService = new QuoteServiceImpl(quoteRowsProvider);

        int year = 2022;
        int month = 7;

        LocalDate wednesday = LocalDate.of(year, month, 20);
        LocalDate thursday = LocalDate.of(year, month, 21);
        LocalDate friday = LocalDate.of(year, month, 22);
        LocalDate saturday = LocalDate.of(year, month, 23);
        LocalDate sunday = LocalDate.of(year, month, 24);
        LocalDate monday = LocalDate.of(year, month, 25);

        List<Quote> quotes;
        List<LocalDate> marketHolidays;

        //WHEN
        quotes = getQuotes(List.of(friday, monday));
        when(quoteRowsProvider.getAllRows()).thenReturn(quotes);
        marketHolidays = quoteService.getMarketHolidays();
        //THEN
        Assertions.assertTrue(marketHolidays.isEmpty());

        //WHEN
        quotes = getQuotes(List.of(thursday, monday));
        when(quoteRowsProvider.getAllRows()).thenReturn(quotes);
        marketHolidays = quoteService.getMarketHolidays();
        //THEN
        Assertions.assertEquals(List.of(friday), marketHolidays);

        //WHEN
        quotes = getQuotes(List.of(thursday, sunday));
        when(quoteRowsProvider.getAllRows()).thenReturn(quotes);
        marketHolidays = quoteService.getMarketHolidays();
        //THEN
        Assertions.assertEquals(List.of(friday), marketHolidays);

        //WHEN
        quotes = getQuotes(List.of(saturday, sunday));
        when(quoteRowsProvider.getAllRows()).thenReturn(quotes);
        marketHolidays = quoteService.getMarketHolidays();
        //THEN
        Assertions.assertTrue(marketHolidays.isEmpty());

        //WHEN
        quotes = getQuotes(List.of(wednesday, friday));
        when(quoteRowsProvider.getAllRows()).thenReturn(quotes);
        marketHolidays = quoteService.getMarketHolidays();
        //THEN
        Assertions.assertEquals(List.of(thursday), marketHolidays);
    }

    @Test
    void whenProviderReturnsOneQuoteReturnException() {
        //GIVEN
        QuoteRowsProvider quoteRowsProvider = mock(QuoteRowsProvider.class);
        QuoteService quoteService = new QuoteServiceImpl(quoteRowsProvider);

        List<Quote> quotes = getOneQuoteForWorkingDay();

        when(quoteRowsProvider.getAllRows()).thenReturn(quotes);

        //THEN
        Assertions.assertThrows(LowCountOfRowsException.class, quoteService::getMarketHolidays);
    }

    @Test
    void whenNoFileReturnsIOException() {
        //GIVEN
        QuoteRowsReader quoteRowsReader = new QuoteRowsReader();
        FileUtils fileUtils = new FileUtils();
        QuoteRowsProvider quoteRowsProvider = new QuoteRowsProvider(quoteRowsReader, fileUtils);
        quoteRowsProvider.setPath("failed-path");
        QuoteService quoteService = new QuoteServiceImpl(quoteRowsProvider);

        //THEN
        Assertions.assertThrows(UncheckedIOException.class, quoteService::getMarketHolidays);
    }

    private List<Quote> getQuotesForWorkingDays() {
        List<Quote> quotes = new ArrayList<>();

        Quote quote1 = new Quote();
        quote1.setDate(LocalDate.of(2022, 6, 30));
        quotes.add(quote1);

        Quote quote2 = new Quote();
        quote2.setDate(LocalDate.of(2022, 7, 1));
        quotes.add(quote2);

        Quote quote3 = new Quote();
        quote3.setDate(LocalDate.of(2022, 7, 6));
        quotes.add(quote3);
        return quotes;
    }

    private List<Quote> getQuotes() {
        List<Quote> quotes = new ArrayList<>();

        Quote quote1 = new Quote();
        quote1.setDate(LocalDate.of(2022, 6, 30));
        quotes.add(quote1);

        Quote quote2 = new Quote();
        quote2.setDate(LocalDate.of(2022, 7, 1));
        quotes.add(quote2);

        Quote quote3 = new Quote();
        quote3.setDate(LocalDate.of(2022, 7, 2));
        quotes.add(quote3);

        Quote quote4 = new Quote();
        quote4.setDate(LocalDate.of(2022, 7, 6));
        quotes.add(quote4);
        return quotes;
    }

    private List<Quote> getUnorderedQuotes() {
        List<Quote> quotes = new ArrayList<>();

        Quote quote1 = new Quote();
        quote1.setDate(LocalDate.of(2022, 6, 30));
        quotes.add(quote1);

        Quote quote2 = new Quote();
        quote2.setDate(LocalDate.of(2022, 7, 2));
        quotes.add(quote2);

        Quote quote3 = new Quote();
        quote3.setDate(LocalDate.of(2022, 7, 1));
        quotes.add(quote3);

        return quotes;
    }

    private List<Quote> getNotUniqueQuotes() {
        List<Quote> quotes = new ArrayList<>();

        Quote quote1 = new Quote();
        quote1.setDate(LocalDate.of(2022, 6, 30));
        quotes.add(quote1);

        Quote quote2 = new Quote();
        quote2.setDate(LocalDate.of(2022, 7, 2));
        quotes.add(quote2);

        Quote quote3 = new Quote();
        quote3.setDate(LocalDate.of(2022, 7, 2));
        quotes.add(quote3);

        return quotes;
    }

    private List<Quote> getOneQuoteForWorkingDay() {
        List<Quote> quotes = new ArrayList<>();

        Quote quote = new Quote();
        quote.setDate(LocalDate.of(2022, 7, 1));
        quotes.add(quote);

        return quotes;
    }

    private List<Quote> getQuotes(List<LocalDate> dates) {
        return dates.stream().map(x -> {
            Quote quote = new Quote();
            quote.setDate(x);
            quote.setVolume(1);
            quote.setClose(BigDecimal.ONE);
            quote.setHigh(BigDecimal.ONE);
            quote.setLow(BigDecimal.ONE);
            quote.setOpen(BigDecimal.ONE);
            quote.setAdjClose(BigDecimal.ONE);
            return quote;
        }).toList();
    }
}