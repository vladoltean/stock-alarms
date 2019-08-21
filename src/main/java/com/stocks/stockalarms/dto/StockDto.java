package com.stocks.stockalarms.dto;

import lombok.Data;

/**
 * By vlad.oltean on 2019-08-21.
 */
@Data
public class StockDto {

    private String symbol;
    private String companyName;
    private Double price;

}
