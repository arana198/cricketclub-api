package com.cricketclub.committee.member.exception;

import com.cricketclub.common.exception.ObjectNotFoundException;

public class NoSuchCommitteeMemberException extends ObjectNotFoundException {
    public NoSuchCommitteeMemberException(Long committeeMemberId) {
        super("Committee member [ " + committeeMemberId + " ] not found");
    }

    public NoSuchCommitteeMemberException() {
        super("Committee member not found");
    }
}
