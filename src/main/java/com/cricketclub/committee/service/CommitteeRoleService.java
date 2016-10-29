package com.cricketclub.committee.service;

import com.cricketclub.committee.domain.CommitteeRoleBO;
import com.cricketclub.committee.dto.CommitteeRoleList;

import java.util.Optional;

public abstract class CommitteeRoleService {

    public abstract Optional<CommitteeRoleList> getActiveCommitteRole();

    abstract Optional<CommitteeRoleBO> findById(final int id);
}
