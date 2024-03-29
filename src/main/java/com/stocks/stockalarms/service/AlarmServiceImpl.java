package com.stocks.stockalarms.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import com.stocks.stockalarms.domain.Alarm;
import com.stocks.stockalarms.domain.MonitoredStock;
import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.domain.event.StockUpdatedEvent;
import com.stocks.stockalarms.dto.AlarmDto;
import com.stocks.stockalarms.dto.AlarmForm;
import com.stocks.stockalarms.dto.PersonWithAlarm;
import com.stocks.stockalarms.repository.AlarmRepository;
import com.stocks.stockalarms.util.Mapper;
import com.stocks.stockalarms.util.MyCollectors;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-20.
 */
@Service
@AllArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmServiceImpl.class);
    private static Function<Person, List<Alarm>> getAlarms = (person) -> {
        return person.getMonitoredStocks()
                .stream()
                .map(MonitoredStock::getAlarms)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    };
    private final MonitoredStockService monitoredStockService;
    private final EmailService emailService;
    private final AlarmRepository alarmRepository;

    @Override
    @Transactional
    public void save(AlarmForm alarmForm, String username) {
        MonitoredStock monitoredStock = monitoredStockService.getMonitoredStock(alarmForm.getStockSymbol(), username);

        Alarm alarm = null;
        if (alarmForm.getId() != null) {
            alarm = alarmRepository.getOne(alarmForm.getId());
        }
        if (alarm == null) {
            alarm = new Alarm();
        }

        alarm.setMonitoredStock(monitoredStock);
        alarm.setRule(alarmForm.getRule());
        alarm.setAlarmPrice(calculateTargetPrice(BigDecimal.valueOf(monitoredStock.getStock().getPrice()), alarmForm.getRule()));
        alarm.setRefferencePrice(monitoredStock.getStock().getPrice());
        alarm.setActive(true);

        alarmRepository.save(alarm);
    }

    @Override
    @Transactional
    public void delete(Long id, String username) {
        // validate user has right to delete it
        List<Alarm> alarms = alarmRepository.findAlarmForPerson(username, id);
        if (CollectionUtils.isEmpty(alarms)) {
            throw new RuntimeException("Not authorized to delete this alarm!");
        }

        alarmRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<AlarmDto> findAllForUser(String username, Sort sort) {
        List<Alarm> alarms = alarmRepository.findAllByMonitoredStockPersonUsername(username, sort);

        return alarms.stream()
                .map(Mapper.toAlarmDto)
                .collect(Collectors.toList());
    }

    @EventListener
    @Transactional
    public void handleStockUpdate(StockUpdatedEvent stockUpdatedEvent) {
        LOGGER.debug("Received stock updated event: {}", stockUpdatedEvent);
        String symbol = stockUpdatedEvent.getStockSymbol();
        BigDecimal stockPrice = stockUpdatedEvent.getPrice();

        List<PersonWithAlarm> personWithAlarms = alarmRepository.findPersonsWithAlarmsForStock(symbol, stockPrice);

        if (personWithAlarms.size() == 0) {
            LOGGER.debug("No alarms triggered...");
            return;
        }

        MultiValueMap<String, PersonWithAlarm> personWithAlarmsMap = personWithAlarms
                .stream()
                .peek(p -> p.setCurrentPrice(stockPrice))
                .collect(MyCollectors.toMultiValueMap(PersonWithAlarm::getUsername, Function.identity()));

        emailService.send(personWithAlarmsMap);

        // set alarms as inactive
        List<Long> alarmIds = personWithAlarms
                .stream()
                .map(PersonWithAlarm::getAlarmId)
                .collect(Collectors.toList());
        alarmRepository.setAlarmStatusForIds(false, LocalDateTime.now(), alarmIds);
    }

    private BigDecimal calculateTargetPrice(BigDecimal initialPrice, String rule) {
        boolean add = rule.startsWith("+");
        double percent = Double.parseDouble(rule.substring(1));

        BigDecimal percentValueOfInitialPrice = (initialPrice.multiply(BigDecimal.valueOf(percent))
                .divide(BigDecimal.valueOf(100)));

        if (add) {
            return initialPrice.add(percentValueOfInitialPrice);
        } else {
            return initialPrice.subtract(percentValueOfInitialPrice);
        }

    }
}
