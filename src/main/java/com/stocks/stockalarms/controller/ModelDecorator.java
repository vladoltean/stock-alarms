package com.stocks.stockalarms.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.stocks.stockalarms.util.UserUtil;

/**
 * By vlad.oltean on 2019-08-18.
 */
@ControllerAdvice
public class ModelDecorator {

    @ModelAttribute("currentUser")
    public String getCurrentUser() {
        return UserUtil.getCurrentUsername();
    }
}
