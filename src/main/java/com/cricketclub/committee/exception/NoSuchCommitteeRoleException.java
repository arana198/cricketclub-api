package com.cricketclub.committee.exception;

import com.cricketclub.common.exception.ObjectNotFoundException;

public class NoSuchCommitteeRoleException extends ObjectNotFoundException {
    public NoSuchCommitteeRoleException() {
        super("Committee role not found");
    }

    public NoSuchCommitteeRoleException(final Integer committeRoleId) {
        super("Committee role " + committeRoleId + " not found");
    }
}
