package com.stocks.stockalarms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.dto.UserLogIn;
import com.stocks.stockalarms.service.PersonService;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-16.
 */
@Controller
@AllArgsConstructor
public class LoginController {

    @GetMapping(path = {"/", "/login"})
    public String loginPage(Model model, String error, String logout){
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }



}
