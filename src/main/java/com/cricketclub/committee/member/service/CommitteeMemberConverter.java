package com.cricketclub.committee.member.service;

import com.cricketclub.committee.member.dto.CommitteeMember;
import com.cricketclub.committee.member.dto.CommitteeMemberList;
import com.cricketclub.committee.member.domain.CommitteeMemberBO;
import com.cricketclub.common.mapper.Converter;
import com.cricketclub.user.service.mapper.UserConverter;
import com.cricketclub.committee.role.service.CommitteeRoleConverter;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserConverter.class, CommitteeRoleConverter.class } )
public abstract class CommitteeMemberConverter implements Converter<CommitteeMemberBO, CommitteeMember, CommitteeMemberList> {

    CommitteeRoleConverter committeeRoleMapper;
    UserConverter userMapper;

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
        return new CommitteeMemberList(committeeMembersList);
    }

}
