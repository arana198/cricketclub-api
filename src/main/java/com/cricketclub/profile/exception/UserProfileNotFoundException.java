package com.cricketclub.profile.exception;

import com.cricketclub.common.exception.ObjectNotFoundException;

public class UserProfileNotFoundException extends ObjectNotFoundException {
    public UserProfileNotFoundException(final Long userId) {
        super("User profile for user [ " + userId + " ] not found");
    }
}
