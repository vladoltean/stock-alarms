package com.stocks.stockalarms.external;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stocks.stockalarms.domain.Stock;
import com.stocks.stockalarms.domain.StockSymbol;
import com.stocks.stockalarms.repository.StockRepository;
import com.stocks.stockalarms.repository.StockSymbolRepository;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-18.
 */
@Component
@AllArgsConstructor
public class StockPoller {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPoller.class);

    private final StockSymbolRepository stockSymbolRepository;
    private final StockRepository stockRepository;
    private final AlphavantageGateway alphavantageGateway;

    @Scheduled(fixedRateString = "${alphavantage.poller-rate}")
    public void getStockData() throws InterruptedException {
        List<StockSymbol> stockSymbols = stockSymbolRepository.findAll();

        Map<String, StockSymbol> symbolStockSymbolMap = stockSymbols
                .stream()
                .collect(Collectors.toMap(StockSymbol::getSymbol, Function.identity()));


        // api permits loading only 5 per minute... so, after 5 wait one minute
        // todo: launch different thread each minute?
        int counter = 0;

        for (String symbol : symbolStockSymbolMap.keySet()) {
            counter++;

            LOGGER.debug(String.format("Loading most recent data for %s", symbol));
            try{
                GlobalQuoteResponse globalQuoteResponse = alphavantageGateway.getMostRecentQuoteBySymbol(symbol);
                saveStock(symbolStockSymbolMap, globalQuoteResponse);
                LOGGER.debug(String.format("Response:%n%s", globalQuoteResponse));
            } catch (Exception e){
                LOGGER.error("Exception polling for %s...", e);
                LOGGER.error("Continue despite error....");
            }


            if (counter == 5) {
                LOGGER.debug("Waiting 1 minute....");
                Thread.sleep(70000);
                counter = 0;
            }

        }

    }

    private void saveStock(Map<String, StockSymbol> symbolStockSymbolMap, GlobalQuoteResponse globalQuoteResponse) {
        Stock stock = new Stock();
        stock.setSymbol(globalQuoteResponse.getSymbol());
        stock.setPrice(Double.valueOf(globalQuoteResponse.getPrice()));
        stock.setChangePercent(Double.valueOf(globalQuoteResponse.getChangePercent().substring(0,
                globalQuoteResponse.getChangePercent().length() - 1)));
        stock.setCompanyName(symbolStockSymbolMap.get(globalQuoteResponse.getSymbol()).getName());
        stockRepository.save(stock);
    }

}
