package com.cricketclub.committee.member.service;

import com.cricketclub.committee.member.dto.CommitteeMember;
import com.cricketclub.committee.member.dto.CommitteeMemberList;
import com.cricketclub.committee.member.domain.CommitteeMemberBO;
import com.cricketclub.user.domain.UserBO;
import com.cricketclub.committee.member.exception.CommitteeMemberAlreadyExistsException;
import com.cricketclub.committee.member.exception.NoSuchCommitteeMemberException;
import com.cricketclub.committee.role.exception.NoSuchCommitteeRoleException;
import com.cricketclub.user.exception.NoSuchUserException;
import com.cricketclub.common.mapper.Converter;
import com.cricketclub.committee.member.repository.CommitteeMemberRepository;
import com.cricketclub.committee.role.service.CommitteeRoleService;
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

    @Autowired
    private CommitteeMemberRepository committeeMemberRepository;

    @Autowired
    private CommitteeRoleService committeeRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private Converter<CommitteeMemberBO, CommitteeMember, CommitteeMemberList> converter;

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

        CommitteeMemberList committeeMemberList = converter.transformToList(converter.transform(committeeMemberBOList));
        return Optional.of(committeeMemberList);
    }

    @Override
    public void addCommitteeMember(final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, CommitteeMemberAlreadyExistsException {
        committeeRoleService.findById(committeeMember.getCommitteeRoleId())
                .orElseThrow(() -> new NoSuchCommitteeRoleException(committeeMember.getCommitteeRoleId()));

        userService.findUserId(committeeMember.getUserId())
                .orElseThrow(() -> new NoSuchUserException(committeeMember.getUserId()));

        if(committeeMemberRepository.findByCommitteeRoleAndYear(committeeMember.getCommitteeRoleId(), committeeMember.getYear()).isPresent()) {
            throw new CommitteeMemberAlreadyExistsException(committeeMember.getCommitteeMemberId(), committeeMember.getYear());
        }

        CommitteeMemberBO committeeMemberBO = converter.transform(committeeMember);
        committeeMemberRepository.save(committeeMemberBO);
    }

    @Override
    public void updateCommitteeMember(final Long id, final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, NoSuchCommitteeMemberException, CommitteeMemberAlreadyExistsException {
        committeeMemberRepository.findById(id)
                .orElseThrow(() -> new NoSuchCommitteeMemberException(id));

        committeeRoleService.findById(committeeMember.getCommitteeRoleId())
                .orElseThrow(() -> new NoSuchCommitteeRoleException(committeeMember.getCommitteeRoleId()));

        userService.findUserId(committeeMember.getUserId())
                .orElseThrow(() -> new NoSuchUserException(committeeMember.getUserId()));

        if(committeeMemberRepository.findByCommitteeRoleAndYear(committeeMember.getCommitteeRoleId(), committeeMember.getYear())
                .filter(committeeMemberBO -> committeeMemberBO.getYear() != committeeMember.getYear())
                .isPresent()) {
            throw new CommitteeMemberAlreadyExistsException(committeeMember.getCommitteeRoleId(), committeeMember.getYear());
        }

        CommitteeMemberBO committeeMemberBO = converter.transform(committeeMember);
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
                .map(cm -> converter.transform(cm))
                .collect(Collectors.toList());

        return new CommitteeMemberList(committeeMembers);
    }

    @Override
    public CommitteeMemberList findByUser(final UserBO userBO) {
        List<CommitteeMember> committeeMembers =  committeeMemberRepository.findByUser(userBO).stream()
                .map(cm -> converter.transform(cm))
                .collect(Collectors.toList());

        return new CommitteeMemberList(committeeMembers);
    }

    @Override
    public Optional<CommitteeMember> findByCommitteeRoleAndYear(final Integer committeeRoleId, final Integer year) {
        return committeeMemberRepository.findByCommitteeRoleAndYear(committeeRoleId, year)
                .map(cm -> converter.transform(cm));
    }
}
