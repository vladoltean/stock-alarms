package com.stocks.stockalarms.dto;

import lombok.Data;

/**
 * By vlad.oltean on 2019-08-20.
 */
@Data
public class AlarmForm {

    private String stockSymbol;

//    private Double refferencePrice;

    /**
     * Rule for calculating the alarmPrice
     */
    private String rule;

}
