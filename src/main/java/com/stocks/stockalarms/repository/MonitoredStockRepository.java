package com.stocks.stockalarms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stocks.stockalarms.domain.MonitoredStock;

/**
 * By vlad.oltean on 2019-08-20.
 */
@Repository
public interface MonitoredStockRepository extends JpaRepository<MonitoredStock, Long> {

    MonitoredStock findByPersonUsernameAndStockSymbol(String username, String stockSymbol);

}
