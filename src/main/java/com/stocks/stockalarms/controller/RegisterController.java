package com.stocks.stockalarms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.stocks.stockalarms.controller.validator.PersonValidator;
import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.service.PersonService;
import com.stocks.stockalarms.service.SecurityService;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-16.
 */
@Controller
@AllArgsConstructor
public class RegisterController {

    private final PersonService personService;
    private final PersonValidator personValidator;
    private final SecurityService securityService;

    @GetMapping(path = "/register")
    public String renderSignUp(Model model) {
        model.addAttribute("person", new Person());
        return "register";
    }

    //TODO: Recheck validations ?
    @PostMapping(path = "/register")
    public String register(@ModelAttribute Person person, BindingResult bindingResult, Model model) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("person", person);
            return "register";
        }

        personService.save(person);
        securityService.autoLogin(person.getUsername(), person.getPassword());

        return "redirect:/stocks";
    }

}
