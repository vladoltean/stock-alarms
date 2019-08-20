package com.stocks.stockalarms.service;

/**
 * By vlad.oltean on 2019-08-18.
 */
public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);

}
