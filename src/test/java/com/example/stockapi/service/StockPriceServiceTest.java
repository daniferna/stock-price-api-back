package com.example.stockapi.service;

import com.example.stockapi.domain.Company;
import com.example.stockapi.provider.StockPriceProvider;
import com.example.stockapi.repository.CompanyRepository;
import com.example.stockapi.web.dto.CompanyPricesResponse;
import com.example.stockapi.web.dto.PriceDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StockPriceServiceTest {

    private final CompanyRepository companyRepository = mock(CompanyRepository.class);
    private final StockPriceProvider provider = mock(StockPriceProvider.class);
    private final StockPriceService service = new StockPriceService(companyRepository, provider);

    @Test
    void returnsPricesForEachCompany() {
        when(companyRepository.findAll()).thenReturn(List.of(
                new Company(1L, "Apple Inc", "AAPL"),
                new Company(2L, "Microsoft Corp", "MSFT")
        ));
        when(provider.fetchLastNDailyCloses("AAPL", 5))
                .thenReturn(List.of(new PriceDto("2025-11-24", 189.32)));
        when(provider.fetchLastNDailyCloses("MSFT", 5))
                .thenReturn(List.of(new PriceDto("2025-11-24", 421.10)));

        List<CompanyPricesResponse> result = service.getLatestPrices();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).ticker()).isEqualTo("AAPL");
        assertThat(result.get(0).prices()).hasSize(1);
    }

}
