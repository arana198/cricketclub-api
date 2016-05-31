package com.cricketclub.exception.user;

import com.cricketclub.exception.ObjectNotFoundException;

import java.util.NoSuchElementException;
import java.util.Optional;

public class NoSuchRoleException extends ObjectNotFoundException {
    public NoSuchRoleException() {
        super("No roles found");
    }

    public NoSuchRoleException(Integer roleId) {
        super("Role [ " + roleId + " ] not found");
    }
}
