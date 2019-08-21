package com.stocks.stockalarms.dto;

import lombok.Data;

/**
 * By vlad.oltean on 2019-08-21.
 */
@Data
public class AlarmDto {

    private double referencePrice;

    private String rule;

    private boolean active;

    private Double alarmPrice;

    /**
     * Variance as percentage from the price when the alarm was defined (referencePrice) and the current price of the monitored stock.
     */
    private Double variance;

    private StockDto stock;

}
