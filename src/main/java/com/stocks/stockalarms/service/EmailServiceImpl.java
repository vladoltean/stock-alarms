package com.stocks.stockalarms.service;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.stocks.stockalarms.dto.PersonWithAlarm;

/**
 * By vlad.oltean on 2019-08-23.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(MultiValueMap<String, PersonWithAlarm> personWithAlarms) {
        personWithAlarms.forEach((key, alarms) -> {
            LOGGER.debug(String.format("Sending the following alarms to %s:", key));
            alarms.forEach(alarm -> LOGGER.debug(alarm.toString()));

            StringBuilder content = new StringBuilder();
            content.append("Hello, ").append(alarms.get(0).getFirstName()).append("!\n");
            alarms.forEach(alarm -> {
                content.append(String.format("Stock %s has reached $%s! Alarm was set to $%s.%n", alarm.getStockSymbol(),
                        alarm.getCurrentPrice(), alarm.getAlarmPrice()));
            });

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(key);
            msg.setSubject("Stock Alarm for "
                    + alarms.stream().map(PersonWithAlarm::getStockSymbol).collect(Collectors.joining(", "))
                    + "\n");
            msg.setText(content.toString());

            javaMailSender.send(msg);

        });
    }
}
