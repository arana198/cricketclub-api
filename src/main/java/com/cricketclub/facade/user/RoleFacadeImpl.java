package com.cricketclub.facade.user;

import com.cricketclub.api.resource.user.Role;
import com.cricketclub.api.resource.user.RoleList;
import com.cricketclub.domain.user.RoleBO;
import com.cricketclub.exception.user.NoSuchRoleException;
import com.cricketclub.facade.mapper.Mapper;
import com.cricketclub.service.user.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
class RoleFacadeImpl implements RoleFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleFacadeImpl.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private Mapper<RoleBO, Role, RoleList> mapper;

    public Optional<RoleList> findActiveRoles() {
        LOGGER.debug("Find all active user roles");
        List<RoleBO> roleBOList = roleService.findActiveRoles();
        if(roleBOList.isEmpty()) {
            LOGGER.debug("User roles not found");
            return Optional.empty();
        }

        RoleList roleList = mapper.transformToList(mapper.transform(roleBOList));
        return Optional.of(roleList);
    }

    @Transactional
    public void updateRole(final Integer roleId, final Boolean selectable) throws NoSuchRoleException {
        LOGGER.debug("Update role {} to visible: {}", roleId, selectable);
        RoleBO roleBO = roleService.findById(roleId)
                .orElseThrow(() -> new NoSuchRoleException(roleId));

        roleBO.setSelectable(selectable);
        roleService.save(roleBO);
    }

}



