package com.stocks.stockalarms.controller.rest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.stockalarms.domain.event.StockUpdatedEvent;
import com.stocks.stockalarms.dto.PersonWithAlarm;
import com.stocks.stockalarms.dto.search.StockSearchItem;
import com.stocks.stockalarms.external.AlphavantageGateway;
import com.stocks.stockalarms.external.GlobalQuoteResponse;
import com.stocks.stockalarms.external.SymbolSearchResponse;
import com.stocks.stockalarms.repository.AlarmRepository;
import com.stocks.stockalarms.service.AlarmService;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-18.
 */
@RestController
@RequestMapping("test")
@AllArgsConstructor
public class TestController {

    private final AlphavantageGateway alphavantageGateway;
    private final AlarmService alarmService;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final AlarmRepository alarmRepository;

    @GetMapping
    public GlobalQuoteResponse test(@RequestParam(name = "symbol") String symbol) {
        return alphavantageGateway.getMostRecentQuoteBySymbol(symbol);
    }

    @GetMapping("search")
    public List<StockSearchItem> testSearch(@RequestParam(name = "query") String q) {
        SymbolSearchResponse symbolSearchResponse = alphavantageGateway.search(q);
        return symbolSearchResponse.getBestMatches()
                .stream()
                .map(item -> new StockSearchItem(item.getSymbol(), item.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("alarm")
    public List<PersonWithAlarm> testAlarm(@RequestParam String value, @RequestParam String symbol) {
        StockUpdatedEvent stockUpdatedEvent = new StockUpdatedEvent(this, symbol, new BigDecimal(value));
        applicationEventPublisher.publishEvent(stockUpdatedEvent);

        return alarmRepository.findPersonsWithAlarmsForStock(symbol, new BigDecimal(value));
    }


}
