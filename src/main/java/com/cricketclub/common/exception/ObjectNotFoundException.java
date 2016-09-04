package com.cricketclub.common.exception;

public abstract class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(final String message) {
        super(message);
    }
}
