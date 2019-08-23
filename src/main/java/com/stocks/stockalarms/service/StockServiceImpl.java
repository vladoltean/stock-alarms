package com.stocks.stockalarms.service;

import java.math.BigDecimal;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stocks.stockalarms.domain.Stock;
import com.stocks.stockalarms.domain.event.StockUpdatedEvent;
import com.stocks.stockalarms.repository.StockRepository;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-23.
 */
@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void save(Stock stock) {
        stockRepository.save(stock);
        applicationEventPublisher.publishEvent(new StockUpdatedEvent(this, stock.getSymbol(), new BigDecimal(stock.getPrice())));
    }
}
