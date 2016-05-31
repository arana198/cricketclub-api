package com.cricketclub.facade.committee;

import com.cricketclub.api.resource.committee.CommitteeMember;
import com.cricketclub.api.resource.committee.CommitteeMemberList;
import com.cricketclub.domain.committee.CommitteeMemberBO;
import com.cricketclub.exception.committee.CommitteeMemberAlreadyExistsException;
import com.cricketclub.exception.committee.NoSuchCommitteeMemberException;
import com.cricketclub.exception.committee.NoSuchCommitteeRoleException;
import com.cricketclub.exception.user.NoSuchUserException;
import com.cricketclub.facade.mapper.Mapper;
import com.cricketclub.service.committee.CommitteeMemberService;
import com.cricketclub.service.committee.CommitteeRoleService;
import com.cricketclub.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Component
class CommitteeMemberFacadeImpl implements CommitteeMemberFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitteeMemberFacadeImpl.class);

    @Autowired
    private CommitteeMemberService committeeMemberService;

    @Autowired
    private CommitteeRoleService committeeRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper<CommitteeMemberBO, CommitteeMember, CommitteeMemberList> mapper;

    public Optional<CommitteeMemberList> getLatestCommitteeMembers() {
        Integer year = ZonedDateTime.now().getYear();
        List<CommitteeMemberBO> committeeMemberBOList = committeeMemberService.findByYear(year);
        if(committeeMemberBOList.isEmpty()) {
            year -= 1;
            committeeMemberBOList = committeeMemberService.findByYear(year);
        }

        if(committeeMemberBOList.isEmpty()) {
            return Optional.empty();
        }

        CommitteeMemberList committeeMemberList = mapper.transformToList(mapper.transform(committeeMemberBOList));
        committeeMemberList.setYear(year);
        return Optional.of(committeeMemberList);
    }

    @Override
    public void addCommitteeMember(final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, CommitteeMemberAlreadyExistsException {
        committeeRoleService.findById(committeeMember.getCommitteeRoleId())
                .orElseThrow(() -> new NoSuchCommitteeRoleException(committeeMember.getCommitteeRoleId()));

        userService.findById(committeeMember.getUserId())
                .orElseThrow(() -> new NoSuchUserException(committeeMember.getUserId()));

        if(committeeMemberService.findByCommitteeRoleAndYear(committeeMember.getCommitteeRoleId(), committeeMember.getYear()).isPresent()) {
            throw new CommitteeMemberAlreadyExistsException(committeeMember.getCommitteeMemberId(), committeeMember.getYear());
        }

        CommitteeMemberBO committeeMemberBO = mapper.transform(committeeMember);
        committeeMemberService.save(committeeMemberBO);
    }

    @Override
    public void updateCommitteeMember(final Long id, final CommitteeMember committeeMember) throws NoSuchCommitteeRoleException, NoSuchUserException, NoSuchCommitteeMemberException, CommitteeMemberAlreadyExistsException {
        committeeMemberService.findById(id)
                .orElseThrow(() -> new NoSuchCommitteeMemberException(id));

        committeeRoleService.findById(committeeMember.getCommitteeRoleId())
                .orElseThrow(() -> new NoSuchCommitteeRoleException(committeeMember.getCommitteeRoleId()));

        userService.findById(committeeMember.getUserId())
                .orElseThrow(() -> new NoSuchUserException(committeeMember.getUserId()));

        if(committeeMemberService.findByCommitteeRoleAndYear(committeeMember.getCommitteeRoleId(), committeeMember.getYear())
                .filter(committeeMemberBO -> committeeMemberBO.getYear() != committeeMember.getYear())
                .isPresent()) {
            throw new CommitteeMemberAlreadyExistsException(committeeMember.getCommitteeRoleId(), committeeMember.getYear());
        }

        CommitteeMemberBO committeeMemberBO = mapper.transform(committeeMember);
        committeeMemberBO.setId(id);
        committeeMemberService.save(committeeMemberBO);
    }

    @Override
    public void deleteCommitteeMember(final Long id) throws NoSuchCommitteeMemberException {
        CommitteeMemberBO committeeMemberBO = committeeMemberService.findById(id)
                .orElseThrow(() -> new NoSuchCommitteeMemberException(id));

        committeeMemberService.remove(committeeMemberBO);
    }
}
