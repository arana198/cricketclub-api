package com.cricketclub.user.service;

import com.cricketclub.user.domain.UserPasswordTokenBO;

import java.util.Optional;

interface UserPasswordTokenService {
	void save(final UserPasswordTokenBO userPasswordTokenBO);
	void delete(final UserPasswordTokenBO userPasswordTokenBO);
	Optional<UserPasswordTokenBO> findByUserIdAndToken(final Long userId, final String token);
	UserPasswordTokenBO findByUserId(final Long userId);
}
