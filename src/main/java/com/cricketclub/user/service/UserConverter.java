package com.cricketclub.user.service;

import com.cricketclub.user.domain.UserBO;
import com.cricketclub.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

class UserConverter implements Converter<UserBO, User> {

    @Autowired
    private RoleListConverter roleListConverter;

    @Override
    public User convert(UserBO source) {
        return new User(
                source.getId(),
                source.getUsername(),
                source.getUsername(),
                null,
                source.getUserProfile().getFirstName(),
                source.getUserProfile().getLastName(),
                source.getUserProfile().getHomeNumber(),
                source.getUserProfile().getMobileNumber(),
                roleListConverter.convert(source.getRoles()));
    }
}
