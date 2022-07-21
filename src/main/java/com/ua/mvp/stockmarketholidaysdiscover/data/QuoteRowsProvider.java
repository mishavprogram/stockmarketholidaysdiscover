package com.ua.mvp.stockmarketholidaysdiscover.data;

import com.ua.mvp.stockmarketholidaysdiscover.model.Quote;
import com.ua.mvp.stockmarketholidaysdiscover.service.impl.QuoteRowsReader;
import com.ua.mvp.stockmarketholidaysdiscover.utils.FileUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuoteRowsProvider implements CsvRowsProvider<Quote> {
    @Getter
    @Setter
    private String path = "data/DATA.csv";//TODO use properties
    private List<Quote> quotes = null;
    private long fileLastModified;
    private final QuoteRowsReader reader;

    private final FileUtils fileUtils;

    public List<Quote> getAllRows() {
        File file = fileUtils.getFileWithUncheckedException(path);
        boolean fileWasModified = fileUtils.fileWasModified(file, fileLastModified);

        if (quotes == null || fileWasModified) {
            fileLastModified = file.lastModified();
            quotes = reader.getRows(path);
        }
        return quotes;
    }


}
