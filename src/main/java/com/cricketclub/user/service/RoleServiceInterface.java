package com.cricketclub.user.service;

import com.cricketclub.user.dto.Role;
import com.cricketclub.user.dto.RoleList;
import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.exception.NoSuchRoleException;

import java.util.Optional;

public interface RoleServiceInterface {
    Optional<RoleList> findActiveRoles();
    void updateRole(final Integer roleId, final Boolean selectable) throws NoSuchRoleException;
}



