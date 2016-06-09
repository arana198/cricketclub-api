package com.cricketclub.user.exception;

import com.cricketclub.common.exception.ObjectNotFoundException;

public class NoSuchUserPasswordTokenException extends ObjectNotFoundException {
    public NoSuchUserPasswordTokenException(Long userId, String token) {
        super("User password token for service [ " + userId + " ] and token [ " + token + " ] not found");
    }
}
