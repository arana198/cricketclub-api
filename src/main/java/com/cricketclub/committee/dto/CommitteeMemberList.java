package com.cricketclub.committee.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CommitteeMemberList extends BaseDomain {

    private final List<CommitteeMember> committeeMembers;

    @JsonCreator
    public CommitteeMemberList(@JsonProperty(value = "committeeMembers", required = true) List<CommitteeMember> committeeMembers) {
        this.committeeMembers = committeeMembers;
    }
}
