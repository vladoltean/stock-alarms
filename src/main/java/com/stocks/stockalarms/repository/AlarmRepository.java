package com.stocks.stockalarms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stocks.stockalarms.domain.Alarm;

/**
 * By vlad.oltean on 2019-08-20.
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByMonitoredStockPersonUsername(String username);


}
