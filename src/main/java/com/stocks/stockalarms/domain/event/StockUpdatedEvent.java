package com.stocks.stockalarms.domain.event;

import java.math.BigDecimal;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

/**
 * By vlad.oltean on 2019-08-23.
 */
@Getter
public class StockUpdatedEvent extends ApplicationEvent {

    private String stockSymbol;
    private BigDecimal price;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public StockUpdatedEvent(Object source, String stockSymbol, BigDecimal price) {
        super(source);
        this.stockSymbol = stockSymbol;
        this.price = price;
    }

}
