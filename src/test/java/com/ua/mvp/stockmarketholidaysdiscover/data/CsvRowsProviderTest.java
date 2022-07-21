package com.ua.mvp.stockmarketholidaysdiscover.data;

import com.ua.mvp.stockmarketholidaysdiscover.model.Quote;
import com.ua.mvp.stockmarketholidaysdiscover.service.impl.QuoteRowsReader;
import com.ua.mvp.stockmarketholidaysdiscover.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CsvRowsProviderTest {

    public static final String PATH = "test_path";

    @Test
    public void whenFileChangedThenReadFromXML() {
        QuoteRowsReader reader = mock(QuoteRowsReader.class);
        FileUtils fileUtils = mock(FileUtils.class);
        QuoteRowsProvider provider = new QuoteRowsProvider(reader, fileUtils);
        provider.setPath(PATH);
        File file = mock(File.class);

        List<Quote> quotes = new ArrayList<>();
        Quote quote = new Quote();
        quote.setDate(LocalDate.of(2022, 1, 1));
        quotes.add(quote);

        when(reader.getRows(PATH)).thenReturn(quotes);
        when(file.lastModified()).thenReturn(1L);
        when(fileUtils.fileWasModified(file, 1L)).thenReturn(true);
        when(fileUtils.getFileWithUncheckedException(PATH)).thenReturn(file);

        provider.getAllRows();
        provider.getAllRows();

        verify(reader, times(2)).getRows(PATH);
    }

    @Test
    public void whenFileSameThenReturnFromCache() {
        QuoteRowsReader reader = mock(QuoteRowsReader.class);
        FileUtils fileUtils = mock(FileUtils.class);
        QuoteRowsProvider provider = new QuoteRowsProvider(reader, fileUtils);
        provider.setPath(PATH);
        File file = mock(File.class);

        List<Quote> quotes = new ArrayList<>();
        Quote quote = new Quote();
        quote.setDate(LocalDate.of(2022, 1, 1));
        quotes.add(quote);

        when(reader.getRows(PATH)).thenReturn(quotes);
        when(file.lastModified()).thenReturn(1L);
        when(fileUtils.fileWasModified(file, 1L)).thenReturn(true);
        when(fileUtils.getFileWithUncheckedException(PATH)).thenReturn(file);

        provider.getAllRows();

        when(fileUtils.fileWasModified(file, 1L)).thenReturn(false);

        provider.getAllRows();

        verify(reader, times(1)).getRows(PATH);
    }

}