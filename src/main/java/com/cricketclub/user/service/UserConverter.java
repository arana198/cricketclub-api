package com.cricketclub.user.service;

import com.cricketclub.user.domain.UserBO;
import com.cricketclub.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

class UserConverter implements Converter<UserBO, User> {

    private final RoleConverter roleConverter;

    @Autowired
    public UserConverter(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

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
                roleConverter.convert(source.getRoles()));
    }

    public UserBO convert(User source) {
        UserBO userBO = new UserBO();
        userBO.setUsername(source.getUsername());
        userBO.setPassword(source.getPassword());
        return userBO;
    }
}
