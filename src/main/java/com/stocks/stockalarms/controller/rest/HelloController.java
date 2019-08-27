package com.stocks.stockalarms.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-16.
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public HelloObject hello() {
        return new HelloObject("Helllooooo");
    }


    @AllArgsConstructor
    private static class HelloObject {

        private String message;
    }

}
