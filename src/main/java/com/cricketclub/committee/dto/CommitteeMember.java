package com.cricketclub.committee.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.cricketclub.user.dto.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
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

    @JsonCreator
    public CommitteeMember(@JsonProperty(value = "committeeMemberId") final Long committeeMemberId,
                           @JsonProperty(value = "committeeRole", required = true) final CommitteeRole committeeRole,
                           @JsonProperty(value = "user", required = true) final User user,
                           @JsonProperty(value = "year", required = true) final Integer year,
                           @JsonProperty(value = "userId", required = true) final Long userId,
                           @JsonProperty(value = "committeeRoleId", required = true) final Integer committeeRoleId) {
        this.committeeMemberId = committeeMemberId;
        this.committeeRole = committeeRole;
        this.user = user;
        this.year = year;
        this.userId = userId;
        this.committeeRoleId = committeeRoleId;
    }
}
