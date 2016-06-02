package com.cricketclub.user.service.mapper;

import com.cricketclub.user.dto.Role;
import com.cricketclub.user.dto.RoleList;
import com.cricketclub.user.domain.RoleBO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class RoleMapper implements com.cricketclub.common.mapper.Mapper<RoleBO, Role, RoleList> {

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