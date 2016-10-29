package com.cricketclub.committee.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommitteeRoleList extends BaseDomain {

    private final List<CommitteeRole> committeeRoles;

    @JsonCreator
    public CommitteeRoleList(@JsonProperty(value = "committeeRoles", required = true) final List<CommitteeRole> committeeRoles) {
        this.committeeRoles = committeeRoles;
    }
}
