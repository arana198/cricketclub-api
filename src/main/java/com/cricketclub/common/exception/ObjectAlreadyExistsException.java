package com.cricketclub.common.exception;

public abstract class ObjectAlreadyExistsException extends Exception {
    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}
