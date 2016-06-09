package com.cricketclub.common.exception;

public abstract class ObjectExpiredException extends Exception {
    public ObjectExpiredException(String message) {
        super(message);
    }
}
