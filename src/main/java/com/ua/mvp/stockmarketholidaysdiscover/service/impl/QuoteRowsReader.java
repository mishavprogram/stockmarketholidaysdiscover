package com.ua.mvp.stockmarketholidaysdiscover.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.ua.mvp.stockmarketholidaysdiscover.model.Quote;
import com.ua.mvp.stockmarketholidaysdiscover.service.CSVRowsReader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class QuoteRowsReader implements CSVRowsReader<Quote> {

    public List<Quote> getRows(String path) {
        Path myPath = Paths.get(path);

        try (BufferedReader br = Files.newBufferedReader(myPath, StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<Quote> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Quote.class);

            CsvToBean<Quote> csvToBean = new CsvToBeanBuilder<Quote>(br)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
