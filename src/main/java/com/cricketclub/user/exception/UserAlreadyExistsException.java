package com.cricketclub.user.exception;

import com.cricketclub.common.exception.ObjectAlreadyExistsException;

public class UserAlreadyExistsException extends ObjectAlreadyExistsException {
    public UserAlreadyExistsException(final String username) {
        super("User [ " + username + " ] already exists");
    }
}
