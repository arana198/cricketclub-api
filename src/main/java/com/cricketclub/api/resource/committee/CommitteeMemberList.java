package com.cricketclub.api.resource.committee;

import com.cricketclub.api.resource.BaseDomain;

import java.util.ArrayList;
import java.util.List;

public class CommitteeMemberList extends BaseDomain {

    private List<CommitteeMember> committeeMembers = new ArrayList<CommitteeMember>();
    private Integer year;

    public List<CommitteeMember> getCommitteeMembers() {
        return committeeMembers;
    }

    public void setCommitteeMembers(List<CommitteeMember> committeeMembers) {
        this.committeeMembers = committeeMembers;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
