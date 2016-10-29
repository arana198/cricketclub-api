package com.cricketclub.user.service;

import com.cricketclub.common.converter.Converter;
import com.cricketclub.user.domain.UserBO;
import com.cricketclub.user.dto.User;
import com.cricketclub.user.dto.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class UserConverter implements Converter<UserBO, User> {

    private final RoleConverter roleConverter;

    @Autowired
    public UserConverter(final RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public User convert(final UserBO source) {
        return new User(
                source.getId(),
                source.getUsername(),
                source.getUsername(),
                roleConverter.convert(source.getRoles()));
    }

    @Override
    public UserBO convert(final User source) {
        UserBO userBO = new UserBO();
        userBO.setUsername(source.getUsername());
        userBO.setPassword(source.getPassword());
        return userBO;
    }

    public UserList convert(final List<UserBO> source) {
        return new UserList(source.stream()
                .map(this::convert)
                .collect(Collectors.toList()));
    }
}
