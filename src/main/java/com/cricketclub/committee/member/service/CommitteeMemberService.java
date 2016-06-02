package com.cricketclub.committee.member.service;

import com.cricketclub.committee.member.dto.CommitteeMember;
import com.cricketclub.committee.member.dto.CommitteeMemberList;
import com.cricketclub.user.domain.UserBO;
import com.cricketclub.committee.member.exception.CommitteeMemberAlreadyExistsException;
import com.cricketclub.committee.member.exception.NoSuchCommitteeMemberException;
import com.cricketclub.committee.role.exception.NoSuchCommitteeRoleException;
import com.cricketclub.user.exception.NoSuchUserException;

import java.util.List;
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
