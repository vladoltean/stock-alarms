package com.stocks.stockalarms.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stocks.stockalarms.dto.AlarmDto;
import com.stocks.stockalarms.dto.AlarmForm;
import com.stocks.stockalarms.service.AlarmService;
import com.stocks.stockalarms.util.UserUtil;

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
    public String alarms(Model model, @RequestParam(required = false) String sortBy) {
        if (StringUtils.isEmpty(sortBy)) {
            sortBy = "monitoredStock.stock.symbol";
        }

        List<AlarmDto> alarms = alarmService.findAllForUser(UserUtil.getCurrentUsername(), processSort(sortBy));
        List<AlarmDto> activeAlarms = alarms
                .stream()
                .filter(AlarmDto::isActive)
                .collect(Collectors.toList());

        List<AlarmDto> inactiveAlarms = alarms
                .stream()
                .filter(a -> !a.isActive())
                .collect(Collectors.toList());

        model.addAttribute("alarms", activeAlarms);
        model.addAttribute("inactiveAlarms", inactiveAlarms);

        return "alarms";
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public void deleteAlarm(@PathVariable(name = "id") Long id){
        alarmService.delete(id, UserUtil.getCurrentUsername());
    }

    private Sort processSort(@RequestParam String sortBy) {
        Sort.Direction direction;
        if (sortBy.startsWith("-")) {
            direction = Sort.Direction.DESC;
            sortBy = sortBy.substring(1);
        } else if (sortBy.startsWith("+")) {
            direction = Sort.Direction.ASC;
            sortBy = sortBy.substring(1);
        } else {
            direction = Sort.Direction.ASC;
        }

        return new Sort(direction, sortBy);
    }

    @PostMapping
    public String saveAlarm(@ModelAttribute @Valid AlarmForm alarmForm, @RequestHeader("Referer") String referer, RedirectAttributes redirectAttributes) throws URISyntaxException {
        alarmService.save(alarmForm);

        redirectAttributes.addFlashAttribute("alarmSaved", true);

        URI refererUri = new URI(referer);
        return String.format("redirect:/%s", refererUri.getPath().substring(1));
    }

}
