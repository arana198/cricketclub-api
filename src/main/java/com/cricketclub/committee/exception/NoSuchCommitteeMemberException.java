package com.cricketclub.committee.exception;

import com.cricketclub.common.exception.ObjectNotFoundException;

public class NoSuchCommitteeMemberException extends ObjectNotFoundException {
    public NoSuchCommitteeMemberException(final Long committeeMemberId) {
        super("Committee member [ " + committeeMemberId + " ] not found");
    }

    public NoSuchCommitteeMemberException() {
        super("Committee member not found");
    }
}
