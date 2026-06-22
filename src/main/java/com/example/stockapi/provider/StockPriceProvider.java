package com.example.stockapi.provider;

import com.example.stockapi.web.dto.PriceDto;

import java.util.List;

/**
 * Source of daily closing prices for a ticker. The Alpha Vantage implementation
 * is the default; activate the {@code mock} profile to run offline.
 */
public interface StockPriceProvider {

    List<PriceDto> fetchLastNDailyCloses(String ticker, int n);
}
