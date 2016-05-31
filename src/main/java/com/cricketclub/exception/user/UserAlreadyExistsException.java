package com.cricketclub.exception.user;

import com.cricketclub.exception.ObjectAlreadyExistsException;

public class UserAlreadyExistsException extends ObjectAlreadyExistsException {
    public UserAlreadyExistsException(String username) {
        super("User [ " + username + " ] not found");
    }
}
