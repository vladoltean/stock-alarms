package com.stocks.stockalarms.external;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stocks.stockalarms.domain.Stock;
import com.stocks.stockalarms.service.StockService;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-18.
 */
@Component
@AllArgsConstructor
public class StockPoller {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPoller.class);

    private final StockService stockService;
    private final AlphavantageGateway alphavantageGateway;

    /**
     * Refreshes stock data for available stocks.
     *
     * @throws InterruptedException -
     */
    @Scheduled(fixedRateString = "${alphavantage.poller-rate}")
    public void getStockData() throws InterruptedException {
        List<String> stockSymbols = stockService.findAll()
                .stream()
                .map(Stock::getSymbol)
                .collect(Collectors.toList());

        LOGGER.debug(String.format("Begin polling for stock symbols: %s", stockSymbols));

        // Alphavantage free API permits loading only 5 stocks per minute... so, after 5 loaded stocks wait one minute
        int loadedStocks = 0;
        for (String symbol : stockSymbols) {
            loadedStocks++;

            LOGGER.debug(String.format("Loading most recent data for %s", symbol));
            try {
                GlobalQuoteResponse globalQuoteResponse = alphavantageGateway.getMostRecentQuoteBySymbol(symbol);
                saveStock(globalQuoteResponse);

                LOGGER.debug(String.format("Response:%n%s", globalQuoteResponse));
            } catch (Exception e) {
                LOGGER.error("Exception polling for %s...", e);
                LOGGER.warn("Continue despite error....");
            }

            if (loadedStocks == 5) {
                LOGGER.debug("Waiting aprox 1 minute....");
                Thread.sleep(65000);
                loadedStocks = 0;
            }

        }

    }

    private void saveStock(GlobalQuoteResponse globalQuoteResponse) {
        Stock stock = new Stock();
        stock.setSymbol(globalQuoteResponse.getSymbol());
        stock.setPrice(Double.valueOf(globalQuoteResponse.getPrice()));
        stock.setChangePercent(Double.valueOf(globalQuoteResponse.getChangePercent().substring(0,
                globalQuoteResponse.getChangePercent().length() - 1)));
        stockService.save(globalQuoteResponse.getSymbol(), null, globalQuoteResponse.getPrice(), globalQuoteResponse.getChangePercent());
    }

}
