package com.cricketclub.committee.role.dto;

import com.cricketclub.common.dto.BaseDomain;

import java.util.ArrayList;
import java.util.List;

public class CommitteeRoleList extends BaseDomain {

    private List<CommitteeRole> committeeRoles = new ArrayList<CommitteeRole>();

    public List<CommitteeRole> getCommitteeRoles() {
        return committeeRoles;
    }

    public void setCommitteeRoles(List<CommitteeRole> committeeRoles) {
        this.committeeRoles = committeeRoles;
    }
}
