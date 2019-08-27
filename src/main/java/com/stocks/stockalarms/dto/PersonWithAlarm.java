package com.stocks.stockalarms.dto;

import java.math.BigDecimal;

import com.stocks.stockalarms.domain.Alarm;
import com.stocks.stockalarms.domain.MonitoredStock;
import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.domain.Stock;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * By vlad.oltean on 2019-08-27.
 */
@Getter
@ToString
public class PersonWithAlarm {

    private String username;

    private String firstName;

    private String stockSymbol;

    private Long alarmId;

    private BigDecimal alarmPrice;

    @Setter
    private BigDecimal currentPrice;

    private BigDecimal initialPrice;

    public PersonWithAlarm() {
    }

    public PersonWithAlarm(MonitoredStock monitoredStock, Person person, Stock stock, Alarm alarm) {
        this.username = person.getUsername();
        this.firstName = person.getFirstName();
        this.stockSymbol = stock.getSymbol();
        this.alarmId = alarm.getId();
        this.initialPrice = new BigDecimal(alarm.getRefferencePrice());
        this.alarmPrice = alarm.getAlarmPrice();
    }

}
