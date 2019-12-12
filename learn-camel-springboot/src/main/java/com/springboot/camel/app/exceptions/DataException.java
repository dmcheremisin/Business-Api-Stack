package com.springboot.camel.app.exceptions;

public class DataException extends RuntimeException {

    public DataException(String message) {
        super(message);
    }
}
