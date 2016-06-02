package com.cricketclub.committee.member.exception;

import com.cricketclub.common.exception.ObjectAlreadyExistsException;

public class CommitteeMemberAlreadyExistsException extends ObjectAlreadyExistsException {
    public CommitteeMemberAlreadyExistsException(Long committeeMemberId) {
        super("Committee member [ " + committeeMemberId + " ] already exists");
    }

    public CommitteeMemberAlreadyExistsException(Integer committeeRoleId, Integer year) {
        super("Committee member for role [ " + committeeRoleId + " ] and year [ " + year + " ] already exists");
    }

    public CommitteeMemberAlreadyExistsException(Long committeeMemberId, Integer year) {
        super("Committee member [ " + committeeMemberId + " ] for year [ " + year + " ] already exists");
    }
}
