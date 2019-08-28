package com.stocks.stockalarms.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stocks.stockalarms.domain.Alarm;
import com.stocks.stockalarms.dto.PersonWithAlarm;

/**
 * By vlad.oltean on 2019-08-20.
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByMonitoredStockPersonUsername(String username, Sort sort);

    @Query(value = "SELECT new com.stocks.stockalarms.dto.PersonWithAlarm(ms, p, s, a) " +
            "FROM MonitoredStock ms JOIN ms.stock s JOIN ms.person p JOIN ms.alarms a " +
            "WHERE s.symbol=(?1) AND a.active=true " +
            "AND ((SUBSTRING(a.rule, 0, 2)='+' AND ?2 >= a.alarmPrice) " +
            "OR (SUBSTRING(a.rule, 0, 2)='-' AND ?2 <= a.alarmPrice)) ")
    List<PersonWithAlarm> findPersonsWithAlarmsForStock(String stockSymbol, BigDecimal price);

    @Query(value = "select a from Alarm a join a.monitoredStock ms join ms.person p where p.username=?1 and a.id=?2")
    List<Alarm> findAlarmForPerson(String username, Long alarmId);

    @Query(value = "update Alarm a set a.active=?1, a.triggeredAt=?2 where a.id in (?3)")
    @Modifying
    void setAlarmStatusForIds(boolean status, LocalDateTime triggeredAt, Collection<Long> ids);

}
