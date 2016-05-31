package com.cricketclub.service.user;

import com.cricketclub.domain.user.UserStatusBO;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserStatusService {
	Optional<UserStatusBO> findById(final Integer id);
	Optional<UserStatusBO> findByName(final UserStatusBO.UserStatus userStatus);
}
