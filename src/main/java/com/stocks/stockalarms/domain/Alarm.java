package com.stocks.stockalarms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * By vlad.oltean on 2019-08-19.
 */
@Entity
@Data
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MonitoredStock monitoredStock;

    private Double refferencePrice;

    /**
     * Rule for calculating the alarmPrice
     */
    private String rule;

    private Double alarmPrice;

    private boolean active;
}
