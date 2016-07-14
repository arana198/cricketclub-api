package com.cricketclub.user.service;

import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.dto.RoleList;
import com.cricketclub.user.exception.NoSuchRoleException;

import java.util.Optional;

public abstract class RoleService {
    public abstract Optional<RoleList> findActiveRoles();
    public abstract void updateRole(final int roleId, final boolean selectable) throws NoSuchRoleException;
    abstract Optional<RoleBO> findById(final int id) throws NoSuchRoleException;
    abstract Optional<RoleBO> findByName(final RoleBO.Role role) throws NoSuchRoleException;
}



