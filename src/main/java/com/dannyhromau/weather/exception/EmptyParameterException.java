package com.dannyhromau.weather.exception;

public class EmptyParameterException extends Exception {
    private String key;

    public EmptyParameterException(String message, String key) {

        super(message);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

