package com.cricketclub.exception.user;

import com.cricketclub.exception.ObjectExpiredException;

import java.util.Date;

public class UserPasswordTokenExpiredException extends ObjectExpiredException {
    public UserPasswordTokenExpiredException(Long userId, String token, Date createdTs) {
        super("User password token for user [ " + userId + " ] and token [ " + token + " ] expired at [ " + createdTs + " ]");
    }
}
