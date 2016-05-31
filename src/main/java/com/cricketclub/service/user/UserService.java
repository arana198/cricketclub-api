package com.cricketclub.service.user;

import com.cricketclub.domain.user.UserBO;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
	void save(final UserBO user);
	Optional<UserBO> findById(final Long userId);
	Optional<UserBO> findByUsername(final String username);
}
