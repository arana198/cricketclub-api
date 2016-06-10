package com.cricketclub.user;

import com.cricketclub.user.domain.UserStatusBO;

import java.util.Optional;

interface UserStatusService {
	Optional<UserStatusBO> findById(final Integer id);
	Optional<UserStatusBO> findByName(final UserStatusBO.UserStatus userStatus);
}
