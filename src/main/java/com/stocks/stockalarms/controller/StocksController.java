package com.stocks.stockalarms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stocks.stockalarms.external.StockPoller;
import com.stocks.stockalarms.repository.StockRepository;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-16.
 */
@Controller
@AllArgsConstructor
public class StocksController {

    // TODO: via service
    private final StockRepository stockRepository;

    @RequestMapping(path = "stocks", method = {RequestMethod.GET, RequestMethod.POST})
    public String stocks(Model model) {
        model.addAttribute("stocks", stockRepository.findAll());
        return "stocks";
    }

}
