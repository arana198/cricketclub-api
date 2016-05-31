package com.cricketclub.facade.user;

import com.cricketclub.api.resource.committee.CommitteeMember;
import com.cricketclub.api.resource.user.Role;
import com.cricketclub.api.resource.user.RoleList;
import com.cricketclub.api.resource.user.User;
import com.cricketclub.api.resource.user.UserList;
import com.cricketclub.domain.user.RoleBO;
import com.cricketclub.domain.user.UserBO;
import com.cricketclub.facade.committee.CommitteeRoleMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public abstract class UserMapper implements com.cricketclub.facade.mapper.Mapper<UserBO, User, UserList> {
    
    RoleMapper roleMapper;

    @Mappings({
            @Mapping(target = "userId", source = "id"),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "email", source = "userProfile.email"),
            @Mapping(target = "firstName", source = "userProfile.firstName"),
            @Mapping(target = "lastName", source = "userProfile.lastName"),
            @Mapping(target = "mobileNumber", source = "userProfile.mobileNumber"),
            @Mapping(target = "homeNumber", source = "userProfile.homeNumber"),
            @Mapping(target = "committeeMemberList", ignore = true),
            @Mapping(target = "links", ignore = true)
    })
    public abstract User transform(final UserBO userBO);

    @Mappings({
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "userProfile", ignore = true),
            @Mapping(target = "userStatusBO", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "createdTs", ignore = true),
            @Mapping(target = "updatedTs", ignore = true),
            @Mapping(target = "updatedBy", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    @InheritInverseConfiguration
    public abstract UserBO transform(final User user);


    public UserList transformToList(final List<User> users) {
        UserList userList = new UserList();
        userList.setUsers(users);
        return userList;
    }
}
