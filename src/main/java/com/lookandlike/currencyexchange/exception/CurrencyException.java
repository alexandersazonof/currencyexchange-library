package com.lookandlike.currencyexchange.exception;

public class CurrencyException extends Exception {

    public CurrencyException() {
        super();
    }

    public CurrencyException(String message) {
        super(message);
    }

    public CurrencyException(Exception e) {
        super(e);
    }

    public CurrencyException(String message, Exception e) {
        super(message, e);
    }
}
