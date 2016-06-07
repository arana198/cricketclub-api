package com.cricketclub.user.service;

import com.cricketclub.user.dto.User;
import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.domain.UserBO;
import com.cricketclub.user.domain.UserPasswordTokenBO;
import com.cricketclub.user.domain.UserStatusBO;
import com.cricketclub.user.exception.*;
import com.cricketclub.user.repository.UserRepository;
import com.cricketclub.user.oauth.TokenRevoker;
import com.cricketclub.user.service.converter.UserBOConverter;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

@Component
class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    private UserPasswordTokenService userPasswordTokenService;

    @Autowired
    private TokenRevoker tokenRevoker;

    @Autowired
    private UserBOConverter userBOConverter;

    @Override
    public User me(Principal principal) {
        OAuth2Authentication activeUser = (OAuth2Authentication) principal;
        return userRepository.findByUsername(((OAuth2Authentication) principal).getPrincipal().toString())
                .map(u -> userBOConverter.convert(u))
                .get();
    }

    @Override
    public Optional<User> findUserId(final Long userId) {
        return Optional.ofNullable(userRepository.findById(userId)
                .map(u -> userBOConverter.convert(u))
                .get());
    }

    @Override
    public void logout(Principal principal) throws NoSuchUserException {
        LOGGER.debug("Logout service {}", principal.getName());
        userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new NoSuchUserException(principal.getName()));

        tokenRevoker.revoke(principal.getName());
    }

    @Override
    @Transactional
    public void createUser(User user, RoleBO.Role role) throws UserAlreadyExistsException, NoSuchRoleException {
        LOGGER.debug("Creating service {}", user.getUsername());
        userRepository.findByUsername(user.getUsername())
                .orElseThrow(() ->  new UserAlreadyExistsException("Username " + user.getUsername() + " already exists"));

        UserBO userBO = userBOConverter.convert(user);
        final Optional<UserStatusBO> userStatusBO = userStatusService.findByName(UserStatusBO.UserStatus.PENDING);
        final RoleBO userRole = roleService.findByName(role)
                .orElseThrow(() -> new NoSuchRoleException(role));

        userBO.setUserStatusBO(userStatusBO.get());
        userBO.getRoles().add(userRole);

        userRepository.save(userBO);
    }

    @Override
    public void updateUser(final User user) throws NoSuchUserException {
        UserBO existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new NoSuchUserException(user.getUserId()));

        if(!existingUser.getUsername().equals(user.getUsername())){
            LOGGER.debug("Changed username from {} to {}",existingUser.getUsername(), user.getUsername());
            existingUser.setUsername(user.getUsername());
            userRepository.save(existingUser);
        }
    }

    @Override
    @Transactional
    public void resetPassword(final Long userId, final String token, final String password) throws NoSuchUserPasswordTokenException, UserPasswordTokenExpiredException {
        UserPasswordTokenBO userPasswordToken = userPasswordTokenService.findByUserIdAndToken(userId, token)
                .filter()
                .orElseThrow(() -> new NoSuchUserPasswordTokenException(userId, token));

        final Integer OFFSET = 48;
        final long oldTimeMiliseconds = userPasswordToken.getCreatedTs().getTime();
        final long currentTimeMiliseconds = new Date().getTime();
        final long offsetMiliseconds = OFFSET * 60 * 60 * 1000;

        if((currentTimeMiliseconds - offsetMiliseconds) > oldTimeMiliseconds){
            LOGGER.debug("Token generated at {} for service id {} and token {} has expired", userPasswordToken.getCreatedTs(), userId, token);
            throw new UserPasswordTokenExpiredException(userId, token, userPasswordToken.getCreatedTs());
        }

        LOGGER.info("Resetting password for service {}", userId);
        UserBO user = userPasswordToken.getUser();
        user.setPassword(DigestUtils.md5Hex(password));

        userRepository.save(user);
        userPasswordTokenService.delete(userPasswordToken);
    }

    @Override
    public void updatePassword(final Long userId, final String password) throws NoSuchUserException {
        LOGGER.info("Update password for service {}", userId);
        UserBO userBO = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(userId));

        LOGGER.debug("Updating password for service {}", userId);
        userBO.setPassword(DigestUtils.md5Hex(password));
        userRepository.save(userBO);
    }

    @Override
    @Transactional
    public void updateUserStatus(final Long userId, final UserStatusBO.UserStatus userStatus) throws NoSuchUserException {
        LOGGER.debug("Update service status for service {}", userId);
        UserBO userBO = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(userId));

        Optional<UserStatusBO> userStatusBO = userStatusService.findByName(userStatus);
        userBO.setUserStatusBO(userStatusBO.get());
        userRepository.save(userBO);
        tokenRevoker.revoke(userId.toString());
    }
}