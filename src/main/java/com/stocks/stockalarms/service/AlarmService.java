package com.stocks.stockalarms.service;

import java.util.List;

import com.stocks.stockalarms.dto.AlarmDto;
import com.stocks.stockalarms.dto.AlarmForm;

/**
 * By vlad.oltean on 2019-08-20.
 */
public interface AlarmService {

    void save(AlarmForm alarmForm);

    List<AlarmDto> findAllForUser(String username);

}
