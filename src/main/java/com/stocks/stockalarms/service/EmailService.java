package com.stocks.stockalarms.service;

import com.stocks.stockalarms.dto.email.EmailData;

/**
 * By vlad.oltean on 2019-08-23.
 */
public interface EmailService {

    void send(EmailData emailData);

}
