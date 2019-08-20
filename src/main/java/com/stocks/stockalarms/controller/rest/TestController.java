package com.stocks.stockalarms.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.stockalarms.external.AlphavantageGateway;
import com.stocks.stockalarms.external.GlobalQuoteResponse;

/**
 * By vlad.oltean on 2019-08-18.
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private AlphavantageGateway alphavantageGateway;

    @GetMapping
    public GlobalQuoteResponse test(@RequestParam(name = "symbol") String symbol){
        return alphavantageGateway.getMostRecentQuoteBySymbol(symbol);
    }


}
