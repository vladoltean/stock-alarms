package com.stocks.stockalarms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stocks.stockalarms.domain.StockSymbol;

/**
 * By vlad.oltean on 2019-08-18.
 */
@Repository
public interface StockSymbolRepository extends JpaRepository<StockSymbol, Long> {

}
