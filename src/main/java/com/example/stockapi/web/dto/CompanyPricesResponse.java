package com.example.stockapi.web.dto;

import java.util.List;

public record CompanyPricesResponse(String name, String ticker, List<PriceDto> prices) {
}
