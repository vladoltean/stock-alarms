package com.stocks.stockalarms.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * By vlad.oltean on 2019-08-21.
 */
@Data
public class AlarmDto {

    private Long id;

    private double referencePrice;

    private String rule;

    private boolean active;

    private BigDecimal alarmPrice;

    /**
     * Variance as percentage from the price when the alarm was defined (referencePrice) and the current price of the monitored stock.
     */
    private Double variance;

    private StockDto stock;

    private String triggeredAt;

}
