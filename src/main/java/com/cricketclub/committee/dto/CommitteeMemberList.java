package com.cricketclub.committee.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommitteeMemberList extends BaseDomain {

    private final List<CommitteeMember> committeeMembers;

    @JsonCreator
    public CommitteeMemberList(@JsonProperty(value = "committeeMembers", required = true) final List<CommitteeMember> committeeMembers) {
        this.committeeMembers = committeeMembers;
    }
}
