package com.cricketclub.facade.user;

import com.cricketclub.api.resource.user.User;
import com.cricketclub.domain.user.RoleBO;
import com.cricketclub.domain.user.UserStatusBO;
import com.cricketclub.exception.user.NoSuchUserException;
import com.cricketclub.exception.user.NoSuchUserPasswordTokenException;
import com.cricketclub.exception.user.UserAlreadyExistsException;
import com.cricketclub.exception.user.UserPasswordTokenExpiredException;

import java.security.Principal;
import java.util.Optional;

public interface UserFacade {
    User me(final Principal principal);
    Optional<User> findUser(final Long userId);
    void logout(final Principal principal) throws NoSuchUserException;
    void createUser(final User user, final RoleBO.Role role) throws UserAlreadyExistsException;
    void updateUser(final User user) throws NoSuchUserException;
    void updatePassword(final Long userId, final String password) throws NoSuchUserException;
    void resetPassword(final Long userId, final String token, final String password) throws NoSuchUserPasswordTokenException, UserPasswordTokenExpiredException;
    void updateUserStatus(final Long userId, final UserStatusBO.UserStatus userStatus) throws NoSuchUserException;
}
