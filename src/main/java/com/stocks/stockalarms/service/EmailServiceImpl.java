package com.stocks.stockalarms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.stocks.stockalarms.dto.PersonWithAlarm;

/**
 * By vlad.oltean on 2019-08-23.
 */
@Service
public class EmailServiceImpl implements EmailService{

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(MultiValueMap<String, PersonWithAlarm> personWithAlarms) {
        personWithAlarms.forEach((key, alarms) -> {
            LOGGER.debug(String.format("Sending the following alarms to %s:", key));
            alarms.forEach(alarm -> LOGGER.debug(alarm.toString()));

            String alarmsInfo = alarms.stream().map(Object::toString).collect(Collectors.joining("\n"));

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("vladoltean1193@gmail.com");

            msg.setSubject("Stock Alarm(s)!");
            msg.setText("Hello, Vlad!\n The following stocks have reached the desired prices: \n" + alarmsInfo);

            javaMailSender.send(msg);

        });
    }
}
