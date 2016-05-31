package com.cricketclub.facade.committee;

import com.cricketclub.api.resource.committee.CommitteeRole;
import com.cricketclub.api.resource.committee.CommitteeRoleList;
import com.cricketclub.domain.committee.CommitteeRoleBO;
import com.cricketclub.facade.mapper.Mapper;
import com.cricketclub.service.committee.CommitteeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class CommitteeRoleFacadeImpl implements CommitteeRoleFacade {

    @Autowired
    private CommitteeRoleService committeeRoleService;

    @Autowired
    private Mapper<CommitteeRoleBO, CommitteeRole, CommitteeRoleList> mapper;

    @Override
    public Optional<CommitteeRoleList> getActiveCommitteRole() {
        List<CommitteeRoleBO> committeeRoleBOList = committeeRoleService.getActiveCommitteeRoles();
        if(committeeRoleBOList.isEmpty()) {
            return Optional.empty();
        }

        CommitteeRoleList committeeRoleList = mapper.transformToList(mapper.transform(committeeRoleBOList));
        return Optional.of(committeeRoleList);
    }
}
