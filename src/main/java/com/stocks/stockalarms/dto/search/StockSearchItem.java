package com.stocks.stockalarms.dto.search;

import lombok.Data;

/**
 * By vlad.oltean on 2019-08-27.
 */
@Data
public class StockSearchItem {

    private String symbol;
    private String name;

    public StockSearchItem(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }
}
