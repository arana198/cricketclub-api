package com.cricketclub.committee.service;

import com.cricketclub.committee.dto.CommitteeMember;
import com.cricketclub.committee.dto.CommitteeMemberList;
import com.cricketclub.user.domain.UserBO;
import com.cricketclub.committee.exception.CommitteeMemberAlreadyExistsException;
import com.cricketclub.committee.exception.NoSuchCommitteeMemberException;
import com.cricketclub.committee.exception.NoSuchCommitteeRoleException;
import com.cricketclub.user.exception.NoSuchUserException;

import java.util.Optional;

public interface CommitteeMemberService {

    Optional<CommitteeMemberList> getLatestCommitteeMembers();

    void addCommitteeMember(final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, CommitteeMemberAlreadyExistsException;

    void updateCommitteeMember(final Long id, final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, NoSuchCommitteeMemberException, CommitteeMemberAlreadyExistsException;

    void deleteCommitteeMember(final Long id) throws NoSuchCommitteeMemberException;

    CommitteeMemberList findByYear(final Integer year);

    CommitteeMemberList findByUser(final UserBO userBO);

    Optional<CommitteeMember> findByCommitteeRoleAndYear(final Integer committeeRoleId, final Integer year);
}
