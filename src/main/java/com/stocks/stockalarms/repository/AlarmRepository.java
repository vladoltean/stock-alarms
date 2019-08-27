package com.stocks.stockalarms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stocks.stockalarms.domain.Alarm;
import com.stocks.stockalarms.dto.PersonWithAlarm;

/**
 * By vlad.oltean on 2019-08-20.
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByMonitoredStockPersonUsername(String username);

    List<Alarm> findAllByMonitoredStockPersonUsername(String username, Sort sort);

    List<Alarm> findAllByMonitoredStockStockSymbol(String stockSymbol);


    @Query(value = "SELECT new com.stocks.stockalarms.dto.PersonWithAlarm(ms, p, s, a) " +
            "FROM MonitoredStock ms JOIN ms.stock s JOIN ms.person p JOIN ms.alarms a " +
            "WHERE s.symbol=(?1)" +
            "AND ((SUBSTRING(a.rule, 0, 2)='+' AND ?2 >= a.alarmPrice) " +
            "OR (SUBSTRING(a.rule, 0, 2)='-' AND ?2 <= a.alarmPrice)) ")
    List<PersonWithAlarm> findPersonsWithAlarmsForStock(String stockSymbol, BigDecimal price);


}
