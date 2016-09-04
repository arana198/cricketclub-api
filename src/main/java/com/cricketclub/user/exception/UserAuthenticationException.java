package com.cricketclub.user.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 8032566286692538282L;

    public UserAuthenticationException(final String message) {
        super(message);
    }
}
