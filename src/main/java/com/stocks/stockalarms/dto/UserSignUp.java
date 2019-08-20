package com.stocks.stockalarms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * By vlad.oltean on 2019-08-16.
 */
@Getter
@Setter
public class UserSignUp {

    @NotBlank
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    private String password1;

    private String password2;

}
