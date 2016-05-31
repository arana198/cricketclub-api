package com.cricketclub.facade.committee;

import com.cricketclub.api.resource.committee.CommitteeMember;
import com.cricketclub.api.resource.committee.CommitteeMemberList;
import com.cricketclub.exception.committee.CommitteeMemberAlreadyExistsException;
import com.cricketclub.exception.committee.NoSuchCommitteeMemberException;
import com.cricketclub.exception.committee.NoSuchCommitteeRoleException;
import com.cricketclub.exception.user.NoSuchUserException;

import java.util.Optional;

public interface CommitteeMemberFacade {

    Optional<CommitteeMemberList> getLatestCommitteeMembers();

    void addCommitteeMember(final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, CommitteeMemberAlreadyExistsException;

    void updateCommitteeMember(final Long id, final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, NoSuchCommitteeMemberException, CommitteeMemberAlreadyExistsException;

    void deleteCommitteeMember(final Long id) throws NoSuchCommitteeMemberException;

}
