package com.cricketclub.user.exception;

import com.cricketclub.common.exception.ObjectNotFoundException;

public class NoSuchUserException extends ObjectNotFoundException {
    public NoSuchUserException(final Long userId) {
        super("User [ " + userId + " ] not found");
    }

    public NoSuchUserException(final String username) {
        super("User [ " + username + " ] not found");
    }
}
