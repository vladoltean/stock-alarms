package com.stocks.stockalarms.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stocks.stockalarms.domain.Alarm;
import com.stocks.stockalarms.domain.MonitoredStock;
import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.domain.Stock;
import com.stocks.stockalarms.domain.event.StockUpdatedEvent;
import com.stocks.stockalarms.dto.AlarmDto;
import com.stocks.stockalarms.dto.AlarmForm;
import com.stocks.stockalarms.repository.AlarmRepository;
import com.stocks.stockalarms.repository.MonitoredStockRepository;
import com.stocks.stockalarms.repository.PersonRepository;
import com.stocks.stockalarms.repository.StockRepository;
import com.stocks.stockalarms.util.Mapper;
import com.stocks.stockalarms.util.UserUtil;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-20.
 */
@Service
@AllArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final MonitoredStockRepository monitoredStockRepository;
    private final AlarmRepository alarmRepository;
    private final StockRepository stockRepository;
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public void save(AlarmForm alarmForm) {

        String username = UserUtil.getCurrentUsername();

        MonitoredStock monitoredStock = monitoredStockRepository.findByPersonUsernameAndStockSymbol(username, alarmForm.getStockSymbol());
        if (monitoredStock == null) {
            Person person = personRepository.findByUsername(username);
            Stock stock = stockRepository.findBySymbol(alarmForm.getStockSymbol());

            monitoredStock = new MonitoredStock();
            monitoredStock.setPerson(person);
            monitoredStock.setStock(stock);
            monitoredStock = monitoredStockRepository.save(monitoredStock);
        }
// TODO: break method
        Alarm alarm = null;
        if (alarmForm.getId() != null) {
            alarm = alarmRepository.getOne(alarmForm.getId());
        }
        if (alarm == null) {
            alarm = new Alarm();
        }

        alarm.setMonitoredStock(monitoredStock);
        alarm.setRule(alarmForm.getRule());
        alarm.setAlarmPrice(calculateTargetPrice(monitoredStock.getStock().getPrice(), alarmForm.getRule()));
        alarm.setRefferencePrice(monitoredStock.getStock().getPrice());

        alarmRepository.save(alarm);
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
        String symbol = stockUpdatedEvent.getStockSymbol();
        BigDecimal stockPrice = stockUpdatedEvent.getPrice();

        List<Alarm> alarms = alarmRepository.findAllByMonitoredStockStockSymbol(symbol);
        List<Alarm> alarmsToTrigger = alarms.stream()
                        .filter(alarm -> new BigDecimal(alarm.getAlarmPrice()).compareTo(stockPrice) <= 0)
                        .collect(Collectors.toList());

        Set<Long> monitoredStockIds = alarmsToTrigger.stream()
                .map(alarm -> alarm.getMonitoredStock().getId())
                .collect(Collectors.toSet());

        Set<Person> personsToNotify = personRepository.findAllByMonitoredStocks(monitoredStockIds);

        // TODO -> identify for which stocks to notify each person -> one mail per person with multiple stock data, if its the case.
    }

    private Double calculateTargetPrice(Double initialPrice, String rule) {
        boolean add = rule.startsWith("+");
        Double percent = Double.valueOf(rule.substring(1));

        Double percentValueOfInitialPrice = (initialPrice * percent / 100);

        if (add) {
            return initialPrice + percentValueOfInitialPrice;
        } else {
            return initialPrice - percentValueOfInitialPrice;
        }

    }
}
