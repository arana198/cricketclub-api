package com.cricketclub.facade.user;

import com.cricketclub.api.resource.committee.CommitteeRole;
import com.cricketclub.api.resource.committee.CommitteeRoleList;
import com.cricketclub.api.resource.user.Role;
import com.cricketclub.api.resource.user.RoleList;
import com.cricketclub.api.resource.user.User;
import com.cricketclub.domain.committee.CommitteeRoleBO;
import com.cricketclub.domain.user.RoleBO;
import com.cricketclub.domain.user.UserBO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class RoleMapper implements com.cricketclub.facade.mapper.Mapper<RoleBO, Role, RoleList> {

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "selectable", ignore = true)
    })
    public abstract RoleBO transform(final Role role);

    @Mappings({
            @Mapping(target = "roleId", source = "id"),
            @Mapping(target = "links", ignore = true)
    })
    public abstract Role transform(final RoleBO roleBO);

    public abstract Set<Role> transform(final Set<RoleBO> roleBO);

    public RoleList transformToList(final List<Role> roles) {
        RoleList roleList = new RoleList();
        roleList.setRoles(roles);
        return roleList;
    }

}