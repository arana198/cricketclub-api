package com.cricketclub.committee.member.dto;

import com.cricketclub.common.dto.BaseDomain;

import java.util.ArrayList;
import java.util.List;

public class CommitteeMemberList extends BaseDomain {

    private final List<CommitteeMember> committeeMembers;

    public CommitteeMemberList(List<CommitteeMember> committeeMembers) {
        this.committeeMembers = committeeMembers;
    }

    public List<CommitteeMember> getCommitteeMembers() {
        return committeeMembers;
    }
}
