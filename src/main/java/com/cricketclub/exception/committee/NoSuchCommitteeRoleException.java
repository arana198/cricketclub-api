package com.cricketclub.exception.committee;

import com.cricketclub.exception.ObjectNotFoundException;

import java.util.NoSuchElementException;
import java.util.Optional;

public class NoSuchCommitteeRoleException extends ObjectNotFoundException {
    public NoSuchCommitteeRoleException() {
        super("Committee role not found");
    }

    public NoSuchCommitteeRoleException(Integer committeRoleId) {
        super("Committee role "+ committeRoleId + " not found");
    }
}
