package com.ua.mvp.stockmarketholidaysdiscover.service;

import com.ua.mvp.stockmarketholidaysdiscover.model.Quote;
import com.ua.mvp.stockmarketholidaysdiscover.service.impl.QuoteRowsReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class CSVRowsReaderTest {

    public static final String TEST_VALID_CSV_PATH = "src/test/testcsv/TEST-1.csv";
    public static final String TEST_EMPTY_CSV_PATH = "src/test/testcsv/EMPTY.csv";

    @Test
    void whenCSVValidAndNotEmptyReturnResult() {
        CSVRowsReader<Quote> quoteCSVRowsReader = new QuoteRowsReader();

        List<Quote> actual = quoteCSVRowsReader.getRows(TEST_VALID_CSV_PATH);
        List<Quote> expected = getQuotes();

        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenCSVEmptyReturnResult() {
        CSVRowsReader<Quote> quoteCSVRowsReader = new QuoteRowsReader();

        List<Quote> rows = quoteCSVRowsReader.getRows(TEST_EMPTY_CSV_PATH);

        Assertions.assertEquals(0, rows.size());
    }

    private List<Quote> getQuotes() {
        List<Quote> quotes = new ArrayList<>();

        Quote quote1 = new Quote();
        quote1.setDate(LocalDate.of(2022, 7, 1));
        quote1.setOpen(BigDecimal.valueOf(1.0));
        quote1.setHigh(BigDecimal.valueOf(2.0));
        quote1.setLow(BigDecimal.valueOf(3.0));
        quote1.setClose(BigDecimal.valueOf(4.0));
        quote1.setAdjClose(BigDecimal.valueOf(5.0));
        quote1.setVolume(414000);

        Quote quote2 = new Quote();
        quote2.setDate(LocalDate.of(2022, 7, 5));
        quote2.setOpen(BigDecimal.valueOf(1.0));
        quote2.setHigh(BigDecimal.valueOf(2.0));
        quote2.setLow(BigDecimal.valueOf(3.0));
        quote2.setClose(BigDecimal.valueOf(4.0));
        quote2.setAdjClose(BigDecimal.valueOf(5.0));
        quote2.setVolume(377600);

        Quote quote3 = new Quote();
        quote3.setDate(LocalDate.of(2022, 7, 6));
        quote3.setOpen(BigDecimal.valueOf(1.0));
        quote3.setHigh(BigDecimal.valueOf(2.0));
        quote3.setLow(BigDecimal.valueOf(3.0));
        quote3.setClose(BigDecimal.valueOf(4.0));
        quote3.setAdjClose(BigDecimal.valueOf(5.0));
        quote3.setVolume(415400);

        quotes.add(quote1);
        quotes.add(quote2);
        quotes.add(quote3);

        return quotes;
    }
}