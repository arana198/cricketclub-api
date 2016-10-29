package com.cricketclub.user.service;

import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.dto.Role;
import com.cricketclub.user.dto.RoleList;
import com.cricketclub.user.exception.NoSuchRoleException;
import com.cricketclub.user.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
class RoleServiceImpl extends RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository, final RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    public Optional<RoleList> findActiveRoles() {
        LOGGER.debug("Find all active service roles");
        return Optional.ofNullable(new RoleList(roleRepository.findBySelectable(true)
                .stream()
                .map(r -> roleConverter.convert(r))
                .collect(Collectors.toList())));
    }

    @Transactional
    public void updateRole(final int roleId, final boolean selectable) throws NoSuchRoleException {
        LOGGER.debug("Update role {} to visible: {}", roleId, selectable);
        RoleBO roleBO = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchRoleException(roleId));

        roleBO.setSelectable(selectable);
        roleRepository.save(roleBO);
    }

    @Override
    public Optional<RoleBO> findById(final int id) throws NoSuchRoleException {
        return roleRepository.findById(id);
    }


    public Optional<RoleBO> findByName(final Role.UserRole role) throws NoSuchRoleException {
        return roleRepository.findByName(role.getValue());
    }
}



