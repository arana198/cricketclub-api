package com.cricketclub.committee.exception;

import com.cricketclub.common.exception.ObjectAlreadyExistsException;

public class CommitteeMemberAlreadyExistsException extends ObjectAlreadyExistsException {
    public CommitteeMemberAlreadyExistsException(final Long committeeMemberId) {
        super("Committee member [ " + committeeMemberId + " ] already exists");
    }

    public CommitteeMemberAlreadyExistsException(final Integer committeeRoleId, final Integer year) {
        super("Committee member for role [ " + committeeRoleId + " ] and year [ " + year + " ] already exists");
    }

    public CommitteeMemberAlreadyExistsException(final Long committeeMemberId, final Integer year) {
        super("Committee member [ " + committeeMemberId + " ] for year [ " + year + " ] already exists");
    }
}
