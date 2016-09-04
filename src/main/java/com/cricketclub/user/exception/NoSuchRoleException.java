package com.cricketclub.user.exception;

import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.common.exception.ObjectNotFoundException;

public class NoSuchRoleException extends ObjectNotFoundException {
    public NoSuchRoleException() {
        super("No roles found");
    }

    public NoSuchRoleException(final Integer roleId) {
        super("role [ " + roleId + " ] not found");
    }

    public NoSuchRoleException(final RoleBO.Role role) {
        super("Role [ " + role + " ] not found");
    }
}
