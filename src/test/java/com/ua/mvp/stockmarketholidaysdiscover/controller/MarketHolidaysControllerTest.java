package com.ua.mvp.stockmarketholidaysdiscover.controller;

import com.ua.mvp.stockmarketholidaysdiscover.exceptions.CsvRowsOrderException;
import com.ua.mvp.stockmarketholidaysdiscover.exceptions.CsvRowsUniqueException;
import com.ua.mvp.stockmarketholidaysdiscover.exceptions.ExceptionMessages;
import com.ua.mvp.stockmarketholidaysdiscover.exceptions.LowCountOfRowsException;
import com.ua.mvp.stockmarketholidaysdiscover.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarketHolidaysController.class)
class MarketHolidaysControllerTest {

    public static final int PORT = 9090;
    public static final String PATH = "/api/v0.2/market-holidays";
    public static final String URL = "http://localhost:" + PORT + PATH;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuoteService service;

    @Test
    public void whenHttpGetReturnResult() throws Exception {
        when(service.getMarketHolidays()).thenReturn(getDates());

        this.mockMvc.perform(get(URL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("2022-01-01")));
    }

    @Test
    public void whenHttpGetAndCSVContainsLess2RowsReturnMessage() throws Exception {
        when(service.getMarketHolidays()).thenThrow(LowCountOfRowsException.class);

        this.mockMvc.perform(get(URL))
                .andDo(print()).andExpect(status().isConflict())
                .andExpect(content().string(containsString(ExceptionMessages.LOW_COUNT_ROWS_EXCEPTION)));
    }

    @Test
    public void whenHttpGetAndCSVContainsUnorderedRowsReturnMessage() throws Exception {
        when(service.getMarketHolidays()).thenThrow(CsvRowsOrderException.class);

        this.mockMvc.perform(get(URL))
                .andDo(print()).andExpect(status().isConflict())
                .andExpect(content().string(containsString(ExceptionMessages.CSV_ROWS_ORDER_EXCEPTION)));
    }

    @Test
    public void whenHttpGetAndCSVContainsNotUniqueRowsReturnMessage() throws Exception {
        when(service.getMarketHolidays()).thenThrow(CsvRowsUniqueException.class);

        this.mockMvc.perform(get(URL))
                .andDo(print()).andExpect(status().isConflict())
                .andExpect(content().string(containsString(ExceptionMessages.CSV_ROWS_NOT_UNIQUE_EXCEPTION)));
    }

    @Test
    public void whenHttpGetAndIOExceptionThenReturnMessage() throws Exception {
        when(service.getMarketHolidays()).thenThrow(UncheckedIOException.class);

        this.mockMvc.perform(get(URL))
                .andDo(print()).andExpect(status().isConflict())
                .andExpect(content().string(containsString(ExceptionMessages.IO_EXCEPTION_MESSAGE)));
    }

    @Test
    public void whenHttpGetAndExceptionThenReturnMessage() throws Exception {
        when(service.getMarketHolidays()).thenThrow(RuntimeException.class);

        this.mockMvc.perform(get(URL))
                .andDo(print()).andExpect(status().isConflict())
                .andExpect(content().string(containsString(ExceptionMessages.GENERIC_EXCEPTION_MESSAGE)));
    }

    private List<LocalDate> getDates() {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(2022, 1, 1));
        return dates;
    }
}