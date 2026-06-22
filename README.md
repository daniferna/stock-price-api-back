# Stock Price API (Spring Boot + JPA + External API)

A small service that exposes the latest closing prices for a set of companies.

- Reads tickers from a relational database (H2 in-memory, schema managed by Liquibase).
- For each ticker, fetches the last 5 daily closing prices from Alpha Vantage
  `TIME_SERIES_DAILY`: https://www.alphavantage.co/documentation/#daily
- Returns the data as JSON. No DB insertion.

## Endpoint

`GET /latest-prices`

```json
[
  {
    "name": "Apple Inc",
    "ticker": "AAPL",
    "prices": [
      { "date": "2025-11-24", "closingPrice": 189.32 },
      { "date": "2025-11-21", "closingPrice": 188.45 }
    ]
  }
]
```

## Run it

Requires Java 17+ and Maven.

```bash
# Offline / no API key needed (deterministic fake prices):
mvn spring-boot:run -Dspring-boot.run.profiles=mock

# Against the real Alpha Vantage API (key provided during the interview):
export ALPHAVANTAGE_API_KEY=your-key   # Windows PowerShell: $env:ALPHAVANTAGE_API_KEY="your-key"
mvn spring-boot:run
```

Then: http://localhost:8000/latest-prices

Run the tests:

```bash
mvn test
```

## Your task (≈60 min)

This service **runs and returns data**, but it was written quickly and is not
production-ready. Treat it like a service your team owns and is on-call for.

1. **Review** — Read the code and note the problems you see. Think about
   correctness, failure modes, performance, operability, and security.
   Be ready to explain *why* each one matters.
2. **Fix** — Pick the issues that matter most and fix them. Tell us how you
   prioritised. (Don't try to fix everything — we care more about your reasoning.)
3. **Extend** — Implement **one** improvement of your choice.

There are no trick questions and no single "right" answer. Talk us through your
thinking as you go.
