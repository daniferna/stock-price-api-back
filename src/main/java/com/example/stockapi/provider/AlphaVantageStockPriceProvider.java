package com.example.stockapi.provider;

import com.example.stockapi.web.dto.PriceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Calls Alpha Vantage TIME_SERIES_DAILY.
 * https://www.alphavantage.co/documentation/#daily
 */
@Component
@Profile("!mock")
public class AlphaVantageStockPriceProvider implements StockPriceProvider {

    private static final Logger log = LoggerFactory.getLogger(AlphaVantageStockPriceProvider.class);
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    private final String apiKey;

    public AlphaVantageStockPriceProvider(@Value("${alphavantage.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PriceDto> fetchLastNDailyCloses(String ticker, int n) {
        RestTemplate restTemplate = new RestTemplate();

        String url = BASE_URL
                + "?function=TIME_SERIES_DAILY"
                + "&symbol=" + ticker
                + "&apikey=" + apiKey;

        log.info("Fetching daily series from {}", url);

        Map<String, Object> body = restTemplate.getForObject(url, Map.class);

        Map<String, Object> series = (Map<String, Object>) body.get("Time Series (Daily)");

        List<PriceDto> prices = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String, Object> entry : series.entrySet()) {
            if (count >= n) {
                break;
            }
            Map<String, Object> day = (Map<String, Object>) entry.getValue();
            double close = Double.parseDouble((String) day.get("4. close"));
            prices.add(new PriceDto(entry.getKey(), close));
            count++;
        }
        return prices;
    }
}
