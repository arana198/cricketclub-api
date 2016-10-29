package com.cricketclub.user.exception;

import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.common.exception.ObjectNotFoundException;
import com.cricketclub.user.dto.Role;

public class NoSuchRoleException extends ObjectNotFoundException {
    public NoSuchRoleException() {
        super("No roles found");
    }

    public NoSuchRoleException(final Integer roleId) {
        super("role [ " + roleId + " ] not found");
    }

    public NoSuchRoleException(final Role.UserRole role) {
        super("UserRole [ " + role + " ] not found");
    }
}
