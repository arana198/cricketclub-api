package com.cricketclub.committee.service;

import com.cricketclub.committee.domain.CommitteeMemberBO;
import com.cricketclub.committee.domain.CommitteeRoleBO;
import com.cricketclub.committee.dto.CommitteeMember;
import com.cricketclub.committee.dto.CommitteeMemberList;
import com.cricketclub.committee.exception.CommitteeMemberAlreadyExistsException;
import com.cricketclub.committee.exception.NoSuchCommitteeMemberException;
import com.cricketclub.committee.exception.NoSuchCommitteeRoleException;
import com.cricketclub.committee.repository.CommitteeMemberRepository;
import com.cricketclub.user.exception.NoSuchUserException;
import com.cricketclub.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class CommitteeMemberServiceImpl implements CommitteeMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitteeMemberServiceImpl.class);

    private final CommitteeMemberRepository committeeMemberRepository;
    private final CommitteeRoleService committeeRoleService;
    private final UserService userService;
    private final CommitteeMemberConverter committeeMemberConverter;

    @Autowired
    public CommitteeMemberServiceImpl(CommitteeMemberRepository committeeMemberRepository, CommitteeRoleService committeeRoleService, UserService userService, CommitteeMemberConverter committeeMemberConverter) {
        this.committeeMemberRepository = committeeMemberRepository;
        this.committeeRoleService = committeeRoleService;
        this.userService = userService;
        this.committeeMemberConverter = committeeMemberConverter;
    }

    public Optional<CommitteeMemberList> getLatestCommitteeMembers() {
        Integer year = ZonedDateTime.now().getYear();
        List<CommitteeMemberBO> committeeMemberBOList = committeeMemberRepository.findByYear(year);
        if(committeeMemberBOList.isEmpty()) {
            year -= 1;
            committeeMemberBOList = committeeMemberRepository.findByYear(year);
        }

        if(committeeMemberBOList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(committeeMemberConverter.convert(committeeMemberBOList));
    }

    @Override
    public void addCommitteeMember(final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, CommitteeMemberAlreadyExistsException {
        CommitteeRoleBO committeeRoleBO = committeeRoleService.findById(committeeMember.getCommitteeRoleId())
                .orElseThrow(() -> new NoSuchCommitteeRoleException(committeeMember.getCommitteeRoleId()));

        userService.findUserId(committeeMember.getUserId())
                .orElseThrow(() -> new NoSuchUserException(committeeMember.getUserId()));

        if(committeeMemberRepository.findByCommitteeRoleAndYear(committeeMember.getCommitteeRoleId(), committeeMember.getYear()).isPresent()) {
            throw new CommitteeMemberAlreadyExistsException(committeeMember.getCommitteeMemberId(), committeeMember.getYear());
        }

        CommitteeMemberBO committeeMemberBO = committeeMemberConverter.convert(committeeMember);
        committeeMemberBO.setCommitteeRole(committeeRoleBO);
        committeeMemberRepository.save(committeeMemberBO);
    }

    @Override
    public void updateCommitteeMember(final Long id, final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, NoSuchCommitteeMemberException, CommitteeMemberAlreadyExistsException {
        committeeMemberRepository.findById(id)
                .orElseThrow(() -> new NoSuchCommitteeMemberException(id));

        CommitteeRoleBO committeeRoleBO = committeeRoleService.findById(committeeMember.getCommitteeRoleId())
                .orElseThrow(() -> new NoSuchCommitteeRoleException(committeeMember.getCommitteeRoleId()));

        userService.findUserId(committeeMember.getUserId())
                .orElseThrow(() -> new NoSuchUserException(committeeMember.getUserId()));

        if(committeeMemberRepository.findByCommitteeRoleAndYear(committeeMember.getCommitteeRoleId(), committeeMember.getYear())
                .filter(committeeMemberBO -> committeeMemberBO.getYear() != committeeMember.getYear())
                .isPresent()) {
            throw new CommitteeMemberAlreadyExistsException(committeeMember.getCommitteeRoleId(), committeeMember.getYear());
        }

        CommitteeMemberBO committeeMemberBO = committeeMemberConverter.convert(committeeMember);
        committeeMemberBO.setCommitteeRole(committeeRoleBO);
        committeeMemberBO.setId(id);
        committeeMemberRepository.save(committeeMemberBO);
    }

    @Override
    public void deleteCommitteeMember(final Long id) throws NoSuchCommitteeMemberException {
        CommitteeMemberBO committeeMemberBO = committeeMemberRepository.findById(id)
                .orElseThrow(() -> new NoSuchCommitteeMemberException(id));

        committeeMemberRepository.delete(committeeMemberBO);
    }

    @Override
    public CommitteeMemberList findByYear(final Integer year) {
        List<CommitteeMember> committeeMembers = committeeMemberRepository.findByYear(year).stream()
                .map(cm -> committeeMemberConverter.convert(cm))
                .collect(Collectors.toList());

        return new CommitteeMemberList(committeeMembers);
    }

    @Override
    public CommitteeMemberList findByUserId(final Long userId) {
        List<CommitteeMember> committeeMembers =  committeeMemberRepository.findByUserId(userId).stream()
                .map(cm -> committeeMemberConverter.convert(cm))
                .collect(Collectors.toList());

        return new CommitteeMemberList(committeeMembers);
    }

    @Override
    public Optional<CommitteeMember> findByCommitteeRoleAndYear(final Integer committeeRoleId, final Integer year) {
        return committeeMemberRepository.findByCommitteeRoleAndYear(committeeRoleId, year)
                .map(cm -> committeeMemberConverter.convert(cm));
    }
}
