package com.cricketclub.user.service;

import com.cricketclub.user.dto.User;
import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.domain.UserStatusBO;
import com.cricketclub.user.exception.*;
import com.cricketclub.user.exception.*;

import java.security.Principal;
import java.util.Optional;

public interface UserService {
    User me(final Principal principal);
    Optional<User> findUserId(final long userId);
    void logout(final Principal principal) throws NoSuchUserException;
    void createUser(final User user, final RoleBO.Role role) throws UserAlreadyExistsException, NoSuchRoleException;
    void updateUser(final User user) throws NoSuchUserException;
    void updatePassword(final long userId, final String password) throws NoSuchUserException;
    void resetPassword(final long userId, final String token, final String password) throws NoSuchUserPasswordTokenException, UserPasswordTokenExpiredException;
    void updateUserStatus(final long userId, final UserStatusBO.UserStatus userStatus) throws NoSuchUserException;
}
