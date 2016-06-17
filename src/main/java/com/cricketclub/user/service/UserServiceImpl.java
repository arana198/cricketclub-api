package com.cricketclub.user.service;

import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.domain.UserBO;
import com.cricketclub.user.domain.UserPasswordTokenBO;
import com.cricketclub.user.domain.UserStatusBO;
import com.cricketclub.user.dto.User;
import com.cricketclub.user.exception.NoSuchRoleException;
import com.cricketclub.user.exception.NoSuchUserException;
import com.cricketclub.user.exception.NoSuchUserPasswordTokenException;
import com.cricketclub.user.exception.UserAlreadyExistsException;
import com.cricketclub.user.exception.UserPasswordTokenExpiredException;
import com.cricketclub.user.oauth.TokenRevoker;
import com.cricketclub.user.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Component
class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserStatusService userStatusService;
    private final UserPasswordTokenService userPasswordTokenService;
    private final TokenRevoker tokenRevoker;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           UserStatusService userStatusService,
                           UserPasswordTokenService userPasswordTokenService,
                           TokenRevoker tokenRevoker,
                           UserConverter userConverter) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userStatusService = userStatusService;
        this.userPasswordTokenService = userPasswordTokenService;
        this.tokenRevoker = tokenRevoker;
        this.userConverter = userConverter;
    }

    @Override
    public User me(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .map(userConverter::convert)
                .get();
    }

    @Override
    public Optional<User> findUserId(final Long userId) {
        return Optional.ofNullable(userRepository.findById(userId)
                .map(userConverter::convert)
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
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(user.getUsername());
        }

        UserBO userBO = userConverter.convert(user);
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
                .orElseThrow(() -> new NoSuchUserPasswordTokenException(userId, token));

        if(userPasswordToken.getCreatedTs().compareTo(LocalDateTime.now().minusDays(2)) <= 0){
            LOGGER.debug("Token generated at {} for service id {} and token {} has expired", userPasswordToken.getCreatedTs(), userId, token);
            throw new UserPasswordTokenExpiredException(userId, token, userPasswordToken.getCreatedTs().plusDays(2));
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
