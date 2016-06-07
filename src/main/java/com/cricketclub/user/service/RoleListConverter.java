package com.cricketclub.user.service;

import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.dto.Role;
import com.cricketclub.user.dto.RoleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class RoleListConverter implements Converter<Set<RoleBO>, RoleList> {

    @Autowired
    private RoleConverter roleConverter;

    @Override
    public RoleList convert(Set<RoleBO> source) {
        return new RoleList(
                source.stream()
                .map(r -> roleConverter.convert(r))
                .collect(Collectors.toList())
        );
    }
}
