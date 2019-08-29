package com.stocks.stockalarms.service;

import org.springframework.stereotype.Service;

import com.stocks.stockalarms.domain.MonitoredStock;
import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.domain.Stock;
import com.stocks.stockalarms.repository.MonitoredStockRepository;
import com.stocks.stockalarms.repository.PersonRepository;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-29.
 */
@Service
@AllArgsConstructor
public class MonitoredStockServiceImpl implements MonitoredStockService {

    private final StockService stockService;

    private final MonitoredStockRepository monitoredStockRepository;
    private final PersonRepository personRepository;


    @Override
    public MonitoredStock getMonitoredStock(String stockSymbol, String username) {
        MonitoredStock monitoredStock = monitoredStockRepository.findByPersonUsernameAndStockSymbol(username, stockSymbol);
        if (monitoredStock == null) {
            Person person = personRepository.findByUsername(username);
            Stock stock = stockService.findOneBySymbol(stockSymbol);

            monitoredStock = new MonitoredStock();
            monitoredStock.setPerson(person);
            monitoredStock.setStock(stock);
            monitoredStock = monitoredStockRepository.save(monitoredStock);
        }
        return monitoredStock;
    }
}
