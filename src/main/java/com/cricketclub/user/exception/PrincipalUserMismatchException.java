package com.cricketclub.user.exception;

public class PrincipalUserMismatchException extends Exception {

    private static final long serialVersionUID = -5464030706060936824L;

    public PrincipalUserMismatchException(final String message) {
        super(message);
    }
}
