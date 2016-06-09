package com.cricketclub.common.exception;

import org.springframework.validation.Errors;

public class BadRequestException extends RuntimeException {

    private Errors errors;

    public BadRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() { return errors; }
}
