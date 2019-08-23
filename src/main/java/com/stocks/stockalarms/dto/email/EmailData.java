package com.stocks.stockalarms.dto.email;

import java.util.Map;

import lombok.Data;

/**
 * By vlad.oltean on 2019-08-23.
 */
@Data
public class EmailData {

    private String destinationEmail;
    private Map<String, String> data;

}
