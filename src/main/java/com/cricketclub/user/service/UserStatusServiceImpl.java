package com.cricketclub.user.service;

import com.cricketclub.user.domain.UserStatusBO;
import com.cricketclub.user.repository.UserStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserStatusServiceImpl implements UserStatusService{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserStatusServiceImpl.class);

	private final UserStatusRepository userStatusRepository;

	@Autowired
	public UserStatusServiceImpl(UserStatusRepository userStatusRepository) {
		this.userStatusRepository = userStatusRepository;
	}

	@Override
	public Optional<UserStatusBO> findById(final Integer id) {
        LOGGER.info("Finding service status by id {}", id);
		return Optional.ofNullable(userStatusRepository.getOne(id));
	}

	@Override
	public Optional<UserStatusBO> findByName(final UserStatusBO.UserStatus userStatus) {
        LOGGER.info("Finding service status by name {}", userStatus);
		return userStatusRepository.findByName(userStatus);
	}
}
