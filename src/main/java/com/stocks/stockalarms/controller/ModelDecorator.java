package com.stocks.stockalarms.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * By vlad.oltean on 2019-08-18.
 */
@ControllerAdvice
public class ModelDecorator {

    //@ModelAttribute("currentUser")
    public User getCurrentUser() {
        Object prinicipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (prinicipal == null || prinicipal.equals("anonymousUser")) {
            return null;
        }

        return (User) prinicipal;
    }
}
