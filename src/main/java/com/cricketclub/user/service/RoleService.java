package com.cricketclub.user.service;

import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.dto.Role;
import com.cricketclub.user.dto.RoleList;
import com.cricketclub.user.exception.NoSuchRoleException;

import java.util.Optional;

public abstract class RoleService implements RoleServiceInterface {
    abstract Optional<RoleBO> findById(final Integer id) throws NoSuchRoleException;
    abstract Optional<RoleBO> findByName(final RoleBO.Role role) throws NoSuchRoleException;
}



