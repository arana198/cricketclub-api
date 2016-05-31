package com.cricketclub.api.resource.committee;

import com.cricketclub.api.resource.BaseDomain;

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
