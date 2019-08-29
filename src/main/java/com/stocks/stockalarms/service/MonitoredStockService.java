package com.stocks.stockalarms.service;

import com.stocks.stockalarms.domain.MonitoredStock;

/**
 * By vlad.oltean on 2019-08-29.
 */
public interface MonitoredStockService {

    MonitoredStock getMonitoredStock(String stockSymbol, String username);
}
