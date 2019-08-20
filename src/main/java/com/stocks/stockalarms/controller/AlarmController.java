package com.stocks.stockalarms.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stocks.stockalarms.dto.AlarmForm;
import com.stocks.stockalarms.service.AlarmService;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-20.
 */
@Controller
@RequestMapping("alarms")
@AllArgsConstructor
public class AlarmController {

    private AlarmService alarmService;

    @GetMapping
    public String alarms() {
        return "alarms";
    }

    @PostMapping
    public String saveAlarm(@ModelAttribute @Valid AlarmForm alarmForm, @RequestHeader("Referer") String referer, RedirectAttributes redirectAttributes) throws URISyntaxException {
        alarmService.save(alarmForm);

        redirectAttributes.addFlashAttribute("alarmSaved", true);

        URI refererUri = new URI(referer);
        return String.format("redirect:/%s", refererUri.getPath().substring(1));
    }

}
