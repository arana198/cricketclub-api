package com.cricketclub.exception.committee;

import com.cricketclub.exception.ObjectNotFoundException;

import java.util.NoSuchElementException;
import java.util.Optional;

public class NoSuchCommitteeMemberException extends ObjectNotFoundException {
    public NoSuchCommitteeMemberException(Long committeeMemberId) {
        super("Committee member [ " + committeeMemberId + " ] not found");
    }

    public NoSuchCommitteeMemberException() {
        super("Committee member not found");
    }
}
