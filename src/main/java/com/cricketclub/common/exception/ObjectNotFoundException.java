package com.cricketclub.common.exception;

public abstract class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
