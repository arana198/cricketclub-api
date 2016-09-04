package com.cricketclub.user.exception;

import com.cricketclub.common.exception.ObjectExpiredException;

import java.time.LocalDateTime;
import java.util.Date;

public class UserPasswordTokenExpiredException extends ObjectExpiredException {
    public UserPasswordTokenExpiredException(final Long userId, final String token, final LocalDateTime createdTs) {
        super("User password token for user [ " + userId + " ] and token [ " + token + " ] expired at [ " + createdTs + " ]");
    }
}
