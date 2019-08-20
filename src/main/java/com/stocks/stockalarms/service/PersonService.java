package com.stocks.stockalarms.service;

import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.dto.UserSignUp;

/**
 * By vlad.oltean on 2019-08-16.
 */
public interface PersonService {

    void save(Person person);

    Person findByUSername(String username);

}
