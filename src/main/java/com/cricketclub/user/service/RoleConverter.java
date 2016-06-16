package com.cricketclub.user.service;

import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.dto.Role;
import com.cricketclub.user.dto.RoleList;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
class RoleConverter implements Converter<RoleBO, Role> {

    @Override
    public Role convert(RoleBO source) {
        return new Role(source.getId(),
                source.getName(),
                source.getDescription(),
                source.getPresedenceOrder());
    }

    public RoleList convert(Set<RoleBO> source) {
        return new RoleList(
                source.stream()
                        .map(this::convert)
                        .collect(Collectors.toList())
        );
    }
}
