package com.ua.mvp.stockmarketholidaysdiscover.data;

import com.ua.mvp.stockmarketholidaysdiscover.model.Quote;
import com.ua.mvp.stockmarketholidaysdiscover.service.impl.QuoteRowsReader;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class QuoteRowsProvider implements CsvRowsProvider<Quote> {
    @Getter
    @Setter
    private String path = "data/TEST.csv";
    private List<Quote> quotes = null;
    private int fileHash;
    @Autowired
    private QuoteRowsReader reader;

    public List<Quote> getAllRows() {
        int hash = Path.of(path).toFile().hashCode();

        if (quotes == null || hash != fileHash) {
            quotes = reader.getRows(path);
        }
        return quotes;
    }
}
