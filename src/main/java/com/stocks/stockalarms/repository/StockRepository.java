package com.stocks.stockalarms.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stocks.stockalarms.domain.Stock;

/**
 * By vlad.oltean on 2019-08-19.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Cacheable(cacheNames = "all-stocks")
    List<Stock> findAll();

    @CacheEvict(cacheNames = "all-stocks")
    Stock save(Stock stock);

    Stock findBySymbol(String symbol);

}
