package com.cricketclub.profile.service;

import com.cricketclub.profile.dto.UserProfile;
import com.cricketclub.profile.exception.UserProfileNotFoundException;

public interface UserProfileService {
    UserProfile findByUserId(Long userId) throws UserProfileNotFoundException;
}
