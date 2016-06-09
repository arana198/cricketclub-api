package com.cricketclub.user.exception;

import com.cricketclub.common.exception.ObjectExpiredException;

import java.util.Date;

public class UserPasswordTokenExpiredException extends ObjectExpiredException {
    public UserPasswordTokenExpiredException(Long userId, String token, Date createdTs) {
        super("User password token for service [ " + userId + " ] and token [ " + token + " ] expired at [ " + createdTs + " ]");
    }
}
