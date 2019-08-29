package com.stocks.stockalarms.exception;

/**
 * By vlad.oltean on 2019-08-29.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
