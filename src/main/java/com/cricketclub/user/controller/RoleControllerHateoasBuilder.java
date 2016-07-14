package com.cricketclub.user.controller;

import com.cricketclub.user.dto.Role;
import com.cricketclub.user.dto.RoleList;
import com.cricketclub.user.exception.NoSuchRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class RoleControllerHateoasBuilder {

    private static final String UPDATE_REL = "update-role";

    private final RoleLinksFactory roleLinksFactory;

    @Autowired
    public RoleControllerHateoasBuilder(RoleLinksFactory roleLinksFactory) {
        this.roleLinksFactory = roleLinksFactory;
    }

    List<Link> buildLinksForFindActiveRoles(final RoleList roleList) throws NoSuchRoleException {
        List<Link> links = new ArrayList<Link>();
        links.add(roleLinksFactory.getFindActiveRolesLink(Link.REL_SELF));

        for(Role role : roleList.getRoles() ) {
            roleLinksFactory.getUpdateRoleLink(role.getRoleId(), false, UPDATE_REL);
        }

        return links;
    }
    List<Link> buildLinksForUpdateRole(final int roleId, final boolean selectable) throws NoSuchRoleException {
        List<Link> links = new ArrayList<Link>();
        links.add(roleLinksFactory.getUpdateRoleLink(roleId, selectable, Link.REL_SELF));
        return links;
    }
}

