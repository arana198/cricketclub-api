package com.cricketclub.committee.service;

import com.cricketclub.committee.dto.CommitteeMember;
import com.cricketclub.committee.dto.CommitteeMemberList;
import com.cricketclub.committee.exception.CommitteeMemberAlreadyExistsException;
import com.cricketclub.committee.exception.NoSuchCommitteeMemberException;
import com.cricketclub.committee.exception.NoSuchCommitteeRoleException;
import com.cricketclub.user.exception.NoSuchUserException;

import java.util.Optional;

public interface CommitteeMemberService {

    Optional<CommitteeMemberList> getLatestCommitteeMembers();

    void addCommitteeMember(final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, CommitteeMemberAlreadyExistsException;

    void updateCommitteeMember(final long id, final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, NoSuchCommitteeMemberException, CommitteeMemberAlreadyExistsException;

    void deleteCommitteeMember(final long id) throws NoSuchCommitteeMemberException;

    CommitteeMemberList findByYear(final int year);

    CommitteeMemberList findByUserId(final long userId);

    Optional<CommitteeMember> findByCommitteeRoleAndYear(final int committeeRoleId, final int year);
}
