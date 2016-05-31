package com.cricketclub.exception.user;

import com.cricketclub.exception.ObjectNotFoundException;

import java.util.NoSuchElementException;

public class NoSuchUserException extends ObjectNotFoundException {
    public NoSuchUserException(Long userId) {
        super("User [ " + userId + " ] not found");
    }

    public NoSuchUserException(String username) {
        super("User [ " + username + " ] not found");
    }
}
