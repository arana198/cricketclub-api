package com.cricketclub.facade.user;

import com.cricketclub.api.resource.user.RoleList;
import com.cricketclub.exception.user.NoSuchRoleException;

import java.util.Optional;

public interface RoleFacade {
    Optional<RoleList> findActiveRoles();
    void updateRole(final Integer roleId, final Boolean selectable) throws NoSuchRoleException;
}



