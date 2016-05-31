package com.cricketclub.facade.committee;

import com.cricketclub.api.resource.committee.CommitteeMember;
import com.cricketclub.api.resource.committee.CommitteeMemberList;
import com.cricketclub.api.resource.committee.CommitteeRole;
import com.cricketclub.api.resource.committee.CommitteeRoleList;
import com.cricketclub.domain.committee.CommitteeMemberBO;
import com.cricketclub.domain.committee.CommitteeRoleBO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CommitteeRoleMapper implements com.cricketclub.facade.mapper.Mapper<CommitteeRoleBO, CommitteeRole, CommitteeRoleList> {

    @Mappings({
            @Mapping(target = "committeeRoleId", source = "id"),
            @Mapping(target = "committeeRole", source = "name"),
            @Mapping(target = "links", ignore = true)
    })
    public abstract CommitteeRole transform(final CommitteeRoleBO committeeRoleBO);

    @InheritInverseConfiguration
    public abstract CommitteeRoleBO transform(final CommitteeRole committeeRole);

    public CommitteeRoleList transformToList(final List<CommitteeRole> committeeRoles) {
        CommitteeRoleList committeeRoleList = new CommitteeRoleList();
        committeeRoleList.setCommitteeRoles(committeeRoles);
        return committeeRoleList;
    }

}
