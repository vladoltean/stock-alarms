package com.stocks.stockalarms.dto;

import lombok.Data;

/**
 * By vlad.oltean on 2019-08-20.
 */
@Data
public class AlarmForm {

    private Long id;

    private String stockSymbol;

    /**
     * Rule for calculating the alarmPrice
     */
    private String rule;

}
