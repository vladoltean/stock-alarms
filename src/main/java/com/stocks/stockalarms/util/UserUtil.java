package com.stocks.stockalarms.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * By vlad.oltean on 2019-08-20.
 */
public class UserUtil {

    public static String getCurrentUsername() {
        Object prinicipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (prinicipal == null || prinicipal.equals("anonymousUser")) {
            return null;
        }

        return ((User) prinicipal).getUsername();
    }

}
