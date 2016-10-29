package com.cricketclub.profile.service;

import com.cricketclub.profile.domain.UserProfileBO;
import com.cricketclub.profile.dto.UserProfile;
import com.cricketclub.profile.exception.UserProfileNotFoundException;
import com.cricketclub.profile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileConverter userProfileConverter;

    @Autowired
    public UserProfileServiceImpl(final UserProfileRepository userProfileRepository, final UserProfileConverter userProfileConverter) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileConverter = userProfileConverter;
    }

    @Override
    public UserProfile findByUserId(final Long userId) throws UserProfileNotFoundException {
        return userProfileRepository.findByUserId(userId)
                .map(userProfileConverter::convert)
                .orElseThrow(() -> new UserProfileNotFoundException(userId));
    }
}
