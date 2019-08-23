package com.stocks.stockalarms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stocks.stockalarms.dto.email.EmailData;

/**
 * By vlad.oltean on 2019-08-23.
 */
@Service
public class EmailServiceImpl implements EmailService{

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public void send(EmailData emailData) {
        LOGGER.info(String.format("Sending email to %s.", emailData.getDestinationEmail()));
        LOGGER.debug(String.format("Email data: %n%s", emailData.getData()));
    }
}
