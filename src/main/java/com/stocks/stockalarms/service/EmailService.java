package com.stocks.stockalarms.service;

import org.springframework.util.MultiValueMap;

import com.stocks.stockalarms.dto.PersonWithAlarm;

/**
 * By vlad.oltean on 2019-08-23.
 */
public interface EmailService {

    void send(MultiValueMap<String, PersonWithAlarm> personWithAlarms);

}
