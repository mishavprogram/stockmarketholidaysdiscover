package com.ua.mvp.stockmarketholidaysdiscover.data;

import java.util.List;

public interface CsvRowsProvider<T> {
    List<T> getAllRows();
}
