package com.cricketclub.user.service;

import com.cricketclub.common.converter.Converter;
import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.dto.Role;
import com.cricketclub.user.dto.RoleList;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
class RoleConverter implements Converter<RoleBO, Role> {

    @Override
    public RoleBO convert(Role source) {
        return new RoleBO();
    }

    @Override
    public Role convert(final RoleBO source) {
        return new Role(source.getId(),
                Role.UserRole.getRoleFromString(source.getName()),
                source.getDescription(),
                source.getPresedenceOrder());
    }

    public RoleList convert(final Set<RoleBO> source) {
        return new RoleList(
                source.stream()
                        .map(this::convert)
                        .collect(Collectors.toList())
        );
    }
}
