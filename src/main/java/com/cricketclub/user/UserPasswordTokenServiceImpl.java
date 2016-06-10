package com.cricketclub.user;

import com.cricketclub.user.domain.UserPasswordTokenBO;
import com.cricketclub.user.repository.UserPasswordTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserPasswordTokenServiceImpl implements UserPasswordTokenService{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPasswordTokenServiceImpl.class);
	
	@Autowired
	private UserPasswordTokenRepository userPasswordTokenRepository;

	@Override
	public void save(final UserPasswordTokenBO userPasswordTokenBO) {
		LOGGER.info("Generating a reset password token for service {}",userPasswordTokenBO.getUser().getId());
		userPasswordTokenRepository.save(userPasswordTokenBO);
	}
	
	@Override
	public UserPasswordTokenBO findByUserId(final Long userId) {
		LOGGER.info("Getting a reset password token for service {}", userId);
		return userPasswordTokenRepository.findByUserId(userId)
				.orElse(new UserPasswordTokenBO());
	}

	@Override
	public Optional<UserPasswordTokenBO> findByUserIdAndToken(final Long userId, final String token) {
		LOGGER.info("Getting a reset password token for service {}", userId);
		return userPasswordTokenRepository.findByUserIdAndToken(userId, token);
	}

	@Override
	public void delete(UserPasswordTokenBO userPasswordTokenBO) {
		LOGGER.info("Remove reset password token for service {} and token {}", userPasswordTokenBO.getUser().getId(), userPasswordTokenBO.getToken());
		userPasswordTokenRepository.delete(userPasswordTokenBO);
	}
}
