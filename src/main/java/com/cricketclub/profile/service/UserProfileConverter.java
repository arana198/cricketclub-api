package com.cricketclub.profile.service;

import com.cricketclub.profile.domain.UserProfileBO;
import com.cricketclub.profile.dto.UserProfile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
class UserProfileConverter implements Converter<UserProfileBO, UserProfile> {

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
