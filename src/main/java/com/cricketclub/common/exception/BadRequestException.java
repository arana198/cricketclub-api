package com.cricketclub.common.exception;

import org.springframework.validation.Errors;

public class BadRequestException extends RuntimeException {

    private final Errors errors;

    public BadRequestException(final String message, final Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
