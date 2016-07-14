package com.cricketclub.user.service;

import com.cricketclub.user.domain.UserStatusBO;

import java.util.Optional;

interface UserStatusService {
	Optional<UserStatusBO> findById(final int id);
	Optional<UserStatusBO> findByName(final UserStatusBO.UserStatus userStatus);
}
