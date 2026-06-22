package com.example.stockapi.service;

import com.example.stockapi.domain.Company;
import com.example.stockapi.provider.StockPriceProvider;
import com.example.stockapi.repository.CompanyRepository;
import com.example.stockapi.web.dto.CompanyPricesResponse;
import com.example.stockapi.web.dto.PriceDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockPriceService {

    private static final int DAILY_CLOSES = 5;

    private final CompanyRepository companyRepository;
    private final StockPriceProvider stockPriceProvider;

    public StockPriceService(CompanyRepository companyRepository, StockPriceProvider stockPriceProvider) {
        this.companyRepository = companyRepository;
        this.stockPriceProvider = stockPriceProvider;
    }

    public List<CompanyPricesResponse> getLatestPrices() {
        List<CompanyPricesResponse> result = new ArrayList<>();
        for (Company company : companyRepository.findAll()) {
            List<PriceDto> prices = stockPriceProvider.fetchLastNDailyCloses(company.getTicker(), DAILY_CLOSES);
            result.add(new CompanyPricesResponse(company.getName(), company.getTicker(), prices));
        }
        return result;
    }
}
