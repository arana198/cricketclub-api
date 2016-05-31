package com.cricketclub.service.user;

import com.cricketclub.domain.user.UserStatusBO;
import com.cricketclub.repository.user.UserStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserStatusServiceImpl implements UserStatusService{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserStatusServiceImpl.class);

	@Autowired
	private UserStatusRepository userStatusRepository;
	
	@Override
	public Optional<UserStatusBO> findById(final Integer id) {
        LOGGER.info("Finding user status by id {}", id);
		return Optional.of(userStatusRepository.getOne(id));
	}

	@Override
	public Optional<UserStatusBO> findByName(final UserStatusBO.UserStatus userStatus) {
        LOGGER.info("Finding user status by name {}", userStatus);
		return userStatusRepository.findByName(userStatus);
	}
}
