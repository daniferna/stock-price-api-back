package com.example.stockapi.provider;

import com.example.stockapi.web.dto.PriceDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Deterministic offline provider so the exercise runs without an Alpha Vantage
 * key or internet, and without burning the free-tier quota (25 requests/day).
 * Enable with: --spring.profiles.active=mock
 */
@Component
@Profile("mock")
public class MockStockPriceProvider implements StockPriceProvider {

    private static final LocalDate REFERENCE_DATE = LocalDate.of(2025, 11, 24);

    @Override
    public List<PriceDto> fetchLastNDailyCloses(String ticker, int n) {
        double base = Math.abs(ticker.hashCode() % 500) + 50;
        List<PriceDto> prices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double close = Math.round((base + i) * 100.0) / 100.0;
            prices.add(new PriceDto(REFERENCE_DATE.minusDays(i).toString(), close));
        }
        return prices;
    }
}
