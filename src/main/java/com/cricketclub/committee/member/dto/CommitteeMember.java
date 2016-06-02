package com.cricketclub.committee.member.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.cricketclub.committee.role.dto.CommitteeRole;
import com.cricketclub.user.dto.User;

import javax.validation.constraints.NotNull;

public class CommitteeMember extends BaseDomain {

    private Long committeeMemberId;
    private CommitteeRole committeeRole;
    private User user;

    @NotNull(message = "year is compulsory")
    private Integer year;

    @NotNull(message = "userId is compulsory")
    private Long userId;

    @NotNull(message = "committeeRoleId is compulsory")
    private Integer committeeRoleId;

    public Long getCommitteeMemberId() {
        return committeeMemberId;
    }

    public void setCommitteeMemberId(Long committeeMemberId) {
        this.committeeMemberId = committeeMemberId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CommitteeRole getCommitteeRole() {
        return committeeRole;
    }

    public void setCommitteeRole(CommitteeRole committeeRole) {
        this.committeeRole = committeeRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCommitteeRoleId() {
        return committeeRoleId;
    }

    public void setCommitteeRoleId(Integer committeeRoleId) {
        this.committeeRoleId = committeeRoleId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
