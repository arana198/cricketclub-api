package com.cricketclub.facade.committee;

import com.cricketclub.api.resource.committee.CommitteeRoleList;

import java.util.Optional;

public interface CommitteeRoleFacade {

    Optional<CommitteeRoleList> getActiveCommitteRole();
}
