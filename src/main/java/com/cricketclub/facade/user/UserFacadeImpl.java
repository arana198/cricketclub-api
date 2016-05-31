package com.cricketclub.facade.user;

import com.cricketclub.api.resource.committee.CommitteeMember;
import com.cricketclub.api.resource.committee.CommitteeMemberList;
import com.cricketclub.api.resource.user.Role;
import com.cricketclub.api.resource.user.RoleList;
import com.cricketclub.api.resource.user.User;
import com.cricketclub.api.resource.user.UserList;
import com.cricketclub.domain.committee.CommitteeMemberBO;
import com.cricketclub.domain.user.RoleBO;
import com.cricketclub.domain.user.UserBO;
import com.cricketclub.domain.user.UserPasswordTokenBO;
import com.cricketclub.domain.user.UserStatusBO;
import com.cricketclub.exception.user.NoSuchUserException;
import com.cricketclub.exception.user.NoSuchUserPasswordTokenException;
import com.cricketclub.exception.user.UserAlreadyExistsException;
import com.cricketclub.exception.user.UserPasswordTokenExpiredException;
import com.cricketclub.facade.mapper.Mapper;
import com.cricketclub.service.TokenRevoker;
import com.cricketclub.service.committee.CommitteeMemberService;
import com.cricketclub.service.user.RoleService;
import com.cricketclub.service.user.UserPasswordTokenService;
import com.cricketclub.service.user.UserService;
import com.cricketclub.service.user.UserStatusService;
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
class UserFacadeImpl implements UserFacade{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    private UserPasswordTokenService userPasswordTokenService;

    @Autowired
    private CommitteeMemberService committeeMemberService;

    @Autowired
    private TokenRevoker tokenRevoker;

    @Autowired
    private Mapper<UserBO, User, UserList> userMapper;

    @Autowired
    private Mapper<CommitteeMemberBO, CommitteeMember, CommitteeMemberList> committeeMemberMapper;

    @Autowired
    private Mapper<RoleBO, Role, RoleList> roleMapper;

    @Override
    public User me(Principal principal) {
        OAuth2Authentication activeUser = (OAuth2Authentication) principal;
        Optional<UserBO> optionalUserBO = userService.findByUsername(((OAuth2Authentication) principal).getPrincipal().toString());
        User user = userMapper.transform(optionalUserBO.get());
        return user;
    }

    @Override
    public Optional<User> findUser(final Long userId) {
        Optional<UserBO> optionalUserBO = userService.findById(userId);
        if(!optionalUserBO.isPresent()) {
            LOGGER.debug("User with id {} not found", userId);
            return Optional.empty();
        }

        User user = userMapper.transform(optionalUserBO.get());
        List<CommitteeMemberBO> committeeMemberBOList = committeeMemberService.findByUser(optionalUserBO.get());
        CommitteeMemberList committeeMemberList = committeeMemberMapper.transformToList(committeeMemberMapper.transform(committeeMemberBOList));
        user.setCommitteeMemberList(committeeMemberList);
        return Optional.of(user);
    }

    @Override
    public void logout(Principal principal) throws NoSuchUserException {
        LOGGER.debug("Logout user {}", principal.getName());
        userService.findByUsername(principal.getName())
                .orElseThrow(() -> new NoSuchUserException(principal.getName()));

        tokenRevoker.revoke(principal.getName());
    }

    @Override
    @Transactional
    public void createUser(User user, RoleBO.Role role) throws UserAlreadyExistsException {
        LOGGER.debug("Creating user {}", user.getUsername());
        userService.findByUsername(user.getUsername())
                .orElseThrow(() ->  new UserAlreadyExistsException("Username " + user.getUsername() + " already exists"));

        UserBO userBO = userMapper.transform(user);
        final Optional<UserStatusBO> userStatusBO = userStatusService.findByName(UserStatusBO.UserStatus.PENDING);
        final Optional<RoleBO> userRole = roleService.findByName(role);

        userBO.setUserStatusBO(userStatusBO.get());
        userBO.getRoles().add(userRole.get());

        userService.save(userBO);
        user.setUserId(userBO.getId());
    }

    @Override
    public void updateUser(final User user) throws NoSuchUserException {
        UserBO existingUser = userService.findById(user.getUserId())
                .orElseThrow(() -> new NoSuchUserException(user.getUserId()));

        if(!existingUser.getUsername().equals(user.getUsername())){
            LOGGER.debug("Changed username from {} to {}",existingUser.getUsername(), user.getUsername());
            existingUser.setUsername(user.getUsername());
            userService.save(existingUser);
        }
    }

    @Override
    @Transactional
    public void resetPassword(final Long userId, final String token, final String password) throws NoSuchUserPasswordTokenException, UserPasswordTokenExpiredException {
        UserPasswordTokenBO userPasswordToken = userPasswordTokenService.findByUserIdAndToken(userId, token)
                .orElseThrow(() -> new NoSuchUserPasswordTokenException(userId, token));

        final Integer OFFSET = 48;
        final long oldTimeMiliseconds = userPasswordToken.getCreatedTs().getTime();
        final long currentTimeMiliseconds = new Date().getTime();
        final long offsetMiliseconds = OFFSET * 60 * 60 * 1000;

        if((currentTimeMiliseconds - offsetMiliseconds) > oldTimeMiliseconds){
            LOGGER.debug("Token generated at {} for user id {} and token {} has expired", userPasswordToken.getCreatedTs(), userId, token);
            throw new UserPasswordTokenExpiredException(userId, token, userPasswordToken.getCreatedTs());
        }

        LOGGER.info("Resetting password for user {}", userId);
        UserBO user = userPasswordToken.getUser();
        user.setPassword(DigestUtils.md5Hex(password));

        userService.save(user);
        userPasswordTokenService.delete(userPasswordToken);
    }

    @Override
    public void updatePassword(final Long userId, final String password) throws NoSuchUserException {
        LOGGER.info("Update password for user {}", userId);
        UserBO userBO = userService.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(userId));

        LOGGER.debug("Updating password for user {}", userId);
        userBO.setPassword(DigestUtils.md5Hex(password));
        userService.save(userBO);
    }

    @Override
    @Transactional
    public void updateUserStatus(final Long userId, final UserStatusBO.UserStatus userStatus) throws NoSuchUserException {
        LOGGER.debug("Update user status for user {}", userId);
        UserBO userBO = userService.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(userId));

        Optional<UserStatusBO> userStatusBO = userStatusService.findByName(userStatus);
        userBO.setUserStatusBO(userStatusBO.get());
        userService.save(userBO);
        tokenRevoker.revoke(userId.toString());
    }
}
