package com.stocks.stockalarms.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.stocks.stockalarms.dto.AlarmDto;
import com.stocks.stockalarms.dto.AlarmForm;

/**
 * By vlad.oltean on 2019-08-20.
 */
public interface AlarmService {

    void save(AlarmForm alarmForm);

    @Transactional
    void delete(Long id, String username);

    List<AlarmDto> findAllForUser(String username, Sort sort);


}
