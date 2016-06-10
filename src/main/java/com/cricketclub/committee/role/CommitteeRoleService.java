package com.cricketclub.committee.role;

import com.cricketclub.committee.role.dto.CommitteeRole;
import com.cricketclub.committee.role.dto.CommitteeRoleList;

import java.util.Optional;

public interface CommitteeRoleService {

    Optional<CommitteeRoleList> getActiveCommitteRole();
    Optional<CommitteeRole> findById(final Integer id);
}
