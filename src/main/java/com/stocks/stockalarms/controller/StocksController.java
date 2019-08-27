package com.stocks.stockalarms.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stocks.stockalarms.dto.MonitorStockForm;
import com.stocks.stockalarms.service.StockService;
import com.stocks.stockalarms.util.UserUtil;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-16.
 */
@Controller
@AllArgsConstructor
public class StocksController {

    // TODO: via service
    private final StockService stockService;

    @RequestMapping(path = "stocks", method = {RequestMethod.GET, RequestMethod.POST})
    public String stocks(Model model) {
        model.addAttribute("stocks", stockService.findAllMonitoredStocksForUser(UserUtil.getCurrentUsername()));
        return "stocks";
    }

    @PostMapping(path = "stocks/monitor")
    public String monitor(@ModelAttribute MonitorStockForm monitorStockForm, @RequestHeader("Referer") String referer, RedirectAttributes redirectAttributes) throws URISyntaxException {
        String currentUsername = UserUtil.getCurrentUsername();

        stockService.addToMonitor(monitorStockForm, currentUsername);

        redirectAttributes.addFlashAttribute("stockSaved", true);
        redirectAttributes.addFlashAttribute("stocks", stockService.findAllMonitoredStocksForUser(currentUsername));

        URI refererUri = new URI(referer);
        return String.format("redirect:/%s", refererUri.getPath().substring(1));
    }

}
