package com.cricketclub.committee.service;

import com.cricketclub.committee.dto.CommitteeMember;
import com.cricketclub.committee.dto.CommitteeMemberList;
import com.cricketclub.committee.domain.CommitteeMemberBO;
import com.cricketclub.common.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class CommitteeMemberConverter implements Converter<CommitteeMemberBO, CommitteeMember> {

    private final CommitteeRoleConverter committeeRoleConverter;

    @Autowired
    public CommitteeMemberConverter(final CommitteeRoleConverter committeeRoleConverter) {
        this.committeeRoleConverter = committeeRoleConverter;
    }

    @Override
    public CommitteeMember convert(final CommitteeMemberBO committeeMemberBO) {
        return new CommitteeMember(
                committeeMemberBO.getId(),
                committeeRoleConverter.convert(committeeMemberBO.getCommitteeRole()),
                null,
                committeeMemberBO.getYear(),
                committeeMemberBO.getUserId(),
                committeeMemberBO.getCommitteeRole().getId()
                );
    }

    @Override
    public CommitteeMemberBO convert(final CommitteeMember committeeMember) {
        CommitteeMemberBO committeeMemberBO = new CommitteeMemberBO();
        committeeMemberBO.setYear(committeeMember.getYear());
        return committeeMemberBO;
    }

    public CommitteeMemberList convert(final List<CommitteeMemberBO> committeeMemberBOList) {
        return new CommitteeMemberList(committeeMemberBOList.stream()
                .map(this::convert)
                .collect(Collectors.toList()));
    }
}
