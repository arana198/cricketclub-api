package com.cricketclub.user.service;

import com.cricketclub.committee.member.service.CommitteeMemberService;
import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.domain.UserBO;
import com.cricketclub.user.dto.Role;
import com.cricketclub.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

class RoleConverter implements Converter<RoleBO, Role> {

    @Override
    public Role convert(RoleBO source) {
        return new Role(source.getId(),
                source.getName(),
                source.getDescription(),
                source.getPresedenceOrder());
    }
}
