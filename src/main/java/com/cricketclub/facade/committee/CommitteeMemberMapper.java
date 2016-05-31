package com.cricketclub.facade.committee;

import com.cricketclub.api.resource.committee.CommitteeMember;
import com.cricketclub.api.resource.committee.CommitteeMemberList;
import com.cricketclub.api.resource.user.Role;
import com.cricketclub.api.resource.user.RoleList;
import com.cricketclub.domain.committee.CommitteeMemberBO;
import com.cricketclub.domain.committee.CommitteeRoleBO;
import com.cricketclub.domain.user.RoleBO;
import com.cricketclub.domain.user.UserBO;
import com.cricketclub.facade.user.UserMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserMapper.class, CommitteeRoleMapper.class } )
public abstract class CommitteeMemberMapper implements com.cricketclub.facade.mapper.Mapper<CommitteeMemberBO, CommitteeMember, CommitteeMemberList> {

    CommitteeRoleMapper committeeRoleMapper;
    UserMapper userMapper;

    @Mappings({
            @Mapping(target = "committeeMemberId", source = "id"),
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "committeeRoleId", source = "committeeRole.id"),
            @Mapping(target = "links", ignore = true)
    })
    public abstract CommitteeMember transform(final CommitteeMemberBO committeeMemberBO);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "createdTs", ignore = true),
            @Mapping(target = "updatedTs", ignore = true)
    })
    public abstract CommitteeMemberBO transform(final CommitteeMember committeeMember);

    public CommitteeMemberList transformToList(final List<CommitteeMember> committeeMembersList) {
        CommitteeMemberList committeeMemberList = new CommitteeMemberList();
        committeeMemberList.setCommitteeMembers(committeeMembersList);
        return committeeMemberList;
    }

}
