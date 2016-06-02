package com.cricketclub.committee.role.service;

import com.cricketclub.committee.role.dto.CommitteeRole;
import com.cricketclub.committee.role.dto.CommitteeRoleList;
import com.cricketclub.committee.role.domain.CommitteeRoleBO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CommitteeRoleMapper implements com.cricketclub.common.mapper.Mapper<CommitteeRoleBO, CommitteeRole, CommitteeRoleList> {

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
