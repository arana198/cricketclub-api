package com.cricketclub.committee.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CommitteeRoleList extends BaseDomain {

    private final List<CommitteeRole> committeeRoles;

    @JsonCreator
    public CommitteeRoleList(@JsonProperty(value = "committeeRoles", required = true) List<CommitteeRole> committeeRoles) {
        this.committeeRoles = committeeRoles;
    }
}
