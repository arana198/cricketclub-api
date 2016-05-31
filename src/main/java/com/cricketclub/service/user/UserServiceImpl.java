package com.cricketclub.service.user;

import com.cricketclub.domain.user.UserBO;
import com.cricketclub.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserServiceImpl implements UserService{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void save(final UserBO user) {
		LOGGER.info("Saving user: ", user.getUsername());
		userRepository.save(user);
	}

	@Override
	public Optional<UserBO> findById(final Long userId) {
		LOGGER.info("Finding user: ", userId);
		return userRepository.findById(userId);
	}

	@Override
	public Optional<UserBO> findByUsername(final String username) {
		LOGGER.info("Finding user vy username: ", username);
		return userRepository.findByUsername(username);
	}
}
