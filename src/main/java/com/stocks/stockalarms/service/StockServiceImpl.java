package com.stocks.stockalarms.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stocks.stockalarms.domain.MonitoredStock;
import com.stocks.stockalarms.domain.Stock;
import com.stocks.stockalarms.domain.event.StockUpdatedEvent;
import com.stocks.stockalarms.dto.MonitorStockForm;
import com.stocks.stockalarms.exception.NotFoundException;
import com.stocks.stockalarms.external.AlphavantageGateway;
import com.stocks.stockalarms.external.GlobalQuoteResponse;
import com.stocks.stockalarms.repository.MonitoredStockRepository;
import com.stocks.stockalarms.repository.PersonRepository;
import com.stocks.stockalarms.repository.StockRepository;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-23.
 */
@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final PersonRepository personRepository;
    private final MonitoredStockRepository monitoredStockRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AlphavantageGateway alphavantageGateway;

    @Override
    @Transactional
    public void save(String symbol, String companyName, String price, String changePercent) {
        Stock stock = stockRepository.findBySymbol(symbol);
        if (stock == null) {
            stock = new Stock();
            stock.setCompanyName(companyName);
        }
        stock.setSymbol(symbol);
        stock.setPrice(new BigDecimal(price).doubleValue());
        stock.setChangePercent(Double.valueOf(changePercent.substring(0, changePercent.length() - 1)));
        stock = stockRepository.save(stock);

        stockRepository.save(stock);
        applicationEventPublisher.publishEvent(new StockUpdatedEvent(this, stock.getSymbol(), new BigDecimal(stock.getPrice())));
    }

    @Override
    @Transactional
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    @Transactional
    public Stock findOneBySymbol(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol);
        if (stock == null) {
            throw new NotFoundException(String.format("Stock not found for symbol %s.", symbol));
        }
        return null;
    }

    @Override
    @Transactional
    public void addToMonitor(MonitorStockForm monitorStockForm, String username) {
        Stock stock = stockRepository.findBySymbol(monitorStockForm.getSymbol());
        if (stock == null) {
            GlobalQuoteResponse globalQuoteResponse = alphavantageGateway.getMostRecentQuoteBySymbol(monitorStockForm.getSymbol());
            stock = new Stock();
            stock.setSymbol(monitorStockForm.getSymbol());
            stock.setCompanyName(monitorStockForm.getName());
            stock.setPrice(new BigDecimal(globalQuoteResponse.getPrice()).doubleValue());
            stock.setChangePercent(Double.valueOf(globalQuoteResponse.getChangePercent().substring(0,
                    globalQuoteResponse.getChangePercent().length() - 1)));
            stock = stockRepository.save(stock);
        }

        MonitoredStock monitoredStock = monitoredStockRepository.findByPersonUsernameAndStockSymbol(username, monitorStockForm.getSymbol());
        if (monitoredStock != null) {
            // already monitored
            return;
        }

        monitoredStock = new MonitoredStock();
        monitoredStock.setStock(stock);
        monitoredStock.setPerson(personRepository.findByUsername(username));
        monitoredStockRepository.save(monitoredStock);
    }

    @Override
    @Transactional
    public List<Stock> findAllMonitoredStocksForUser(String username) {
        return stockRepository.findAllByMonitoredStocksPersonUsername(username);
    }
}
