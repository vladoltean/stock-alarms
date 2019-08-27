package com.stocks.stockalarms.util;

import java.util.function.Function;

import org.springframework.beans.BeanUtils;

import com.stocks.stockalarms.domain.Alarm;
import com.stocks.stockalarms.domain.Stock;
import com.stocks.stockalarms.dto.AlarmDto;
import com.stocks.stockalarms.dto.StockDto;

/**
 * By vlad.oltean on 2019-08-21.
 */
public final class Mapper {

    private Mapper() {
    }

    public static final Function<Stock, StockDto> toStockDto = (Stock stock) -> {
        StockDto stockDto = new StockDto();
        BeanUtils.copyProperties(stock, stockDto);
        return stockDto;
    };

    public static final Function<Alarm, AlarmDto> toAlarmDto = (Alarm alarm) -> {
        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setId(alarm.getId());
        alarmDto.setActive(alarm.isActive());
        alarmDto.setAlarmPrice(alarm.getAlarmPrice());
        alarmDto.setReferencePrice(alarm.getRefferencePrice());
        alarmDto.setRule(alarm.getRule());
        alarmDto.setStock(toStockDto.apply(alarm.getMonitoredStock().getStock()));
        alarmDto.setVariance(getVarianceAsPercentage(alarm.getRefferencePrice(), alarmDto.getStock().getPrice()));

        return alarmDto;
    };

    /**
     * Calculates the percentage of the initial value that is needed to get (by summing) to the currentValue.
     */
    private static double getVarianceAsPercentage(double initialValue, double currentValue) {
        return (100 * (currentValue - initialValue) / initialValue);

    }

    public static void main(String[] args) {
        System.out.println(getVarianceAsPercentage(10, 8));
    }


}
