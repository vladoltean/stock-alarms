package com.stocks.stockalarms.controller.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.service.PersonService;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-16.
 */
@RestController
@AllArgsConstructor
public class UserController {

    private final PersonService personService;

    @PostMapping("/users/register")
    public void register(@RequestBody Person person) {
        personService.save(person);
    }

}
