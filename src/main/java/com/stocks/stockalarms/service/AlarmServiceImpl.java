package com.stocks.stockalarms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stocks.stockalarms.domain.Alarm;
import com.stocks.stockalarms.domain.MonitoredStock;
import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.domain.Stock;
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
    public List<AlarmDto> findAllForUser(String username) {
        List<Alarm> alarms = alarmRepository.findAllByMonitoredStockPersonUsername(username);

        return alarms.stream()
                .map(Mapper.toAlarmDto)
                .collect(Collectors.toList());
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
