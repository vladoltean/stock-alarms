package com.stocks.stockalarms.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.stocks.stockalarms.domain.Stock;
import com.stocks.stockalarms.dto.MonitorStockForm;

/**
 * By vlad.oltean on 2019-08-23.
 */
public interface StockService {

    void save(String symbol, String companyName, String price, String changePercent);

    List<Stock> findAll();

    Stock findOneBySymbol(String symbol);

    void addToMonitor(MonitorStockForm monitorStockForm, String username);

    List<Stock> findAllMonitoredStocksForUser(String username);

}
