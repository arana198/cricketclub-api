package com.cricketclub.committee.member.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.cricketclub.committee.role.dto.CommitteeRole;
import com.cricketclub.user.dto.User;

import javax.validation.constraints.NotNull;

public class CommitteeMember extends BaseDomain {

    private final Long committeeMemberId;
    private final CommitteeRole committeeRole;
    private final User user;

    @NotNull(message = "year is compulsory")
    private final Integer year;

    @NotNull(message = "userId is compulsory")
    private final Long userId;

    @NotNull(message = "committeeRoleId is compulsory")
    private final Integer committeeRoleId;

    public CommitteeMember(Long committeeMemberId, CommitteeRole committeeRole, User user, Integer year, Long userId, Integer committeeRoleId) {
        this.committeeMemberId = committeeMemberId;
        this.committeeRole = committeeRole;
        this.user = user;
        this.year = year;
        this.userId = userId;
        this.committeeRoleId = committeeRoleId;
    }

    public Long getCommitteeMemberId() {
        return committeeMemberId;
    }

    public CommitteeRole getCommitteeRole() {
        return committeeRole;
    }

    public User getUser() {
        return user;
    }

    public Integer getYear() {
        return year;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getCommitteeRoleId() {
        return committeeRoleId;
    }
}
