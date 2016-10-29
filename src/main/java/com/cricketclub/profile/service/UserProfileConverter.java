package com.cricketclub.profile.service;

import com.cricketclub.common.converter.Converter;
import com.cricketclub.profile.domain.UserProfileBO;
import com.cricketclub.profile.dto.UserProfile;
import org.springframework.stereotype.Service;

@Service
class UserProfileConverter implements Converter<UserProfileBO, UserProfile> {

    @Override
    public UserProfileBO convert(UserProfile source) {
        return null;
    }

    @Override
    public UserProfile convert(final UserProfileBO source) {
        return UserProfile.builder()
                .userId(source.getUserId())
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .description(source.getDescription())
                .dateOfBirth(source.getDateOfBirth())
                .homeNumber(source.getHomeNumber())
                .mobileNumber(source.getMobileNumber())
                .imageUrl(source.getImageUrl())
                .build();
    }
}
