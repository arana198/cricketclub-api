package com.cricketclub.service.user;

import com.cricketclub.domain.user.RoleBO;
import com.cricketclub.domain.user.UserPasswordTokenBO;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserPasswordTokenService {
	void save(final UserPasswordTokenBO userPasswordTokenBO);
	void delete(final UserPasswordTokenBO userPasswordTokenBO);
	Optional<UserPasswordTokenBO> findByUserIdAndToken(final Long userId, final String token);
	UserPasswordTokenBO findByUserId(final Long userId);
}
