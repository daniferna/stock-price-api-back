package com.example.stockapi.web;

import com.example.stockapi.service.StockPriceService;
import com.example.stockapi.web.dto.CompanyPricesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockPriceController {

    private final StockPriceService stockPriceService;

    public StockPriceController(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    @GetMapping("/latest-prices")
    public List<CompanyPricesResponse> latestPrices() {
        return stockPriceService.getLatestPrices();
    }
}
