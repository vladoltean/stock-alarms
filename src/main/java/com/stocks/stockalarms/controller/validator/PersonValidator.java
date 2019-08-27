package com.stocks.stockalarms.controller.validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.stocks.stockalarms.domain.Person;
import com.stocks.stockalarms.service.PersonService;

import lombok.AllArgsConstructor;

/**
 * By vlad.oltean on 2019-08-18.
 */
@Component
@AllArgsConstructor
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Person.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        if(!isValidEmailAddress(person.getUsername())){
            errors.rejectValue("username", "NotEmail.userForm.email", "Invalid email address!");
        }

        if (personService.findByUSername(person.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username", "Email already used!");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (person.getPassword().length() < 4 || person.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password", "Password should have between 5 and 32 characters.");
        }

        if (StringUtils.isEmpty(person.getPassword()) || !person.getPasswordConfirm().equals(person.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm", "Passwords do not match");
        }
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
