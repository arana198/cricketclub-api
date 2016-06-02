package com.cricketclub.user.exception;

import com.cricketclub.common.exception.ObjectNotFoundException;

public class NoSuchUserException extends ObjectNotFoundException {
    public NoSuchUserException(Long userId) {
        super("User [ " + userId + " ] not found");
    }

    public NoSuchUserException(String username) {
        super("User [ " + username + " ] not found");
    }
}
