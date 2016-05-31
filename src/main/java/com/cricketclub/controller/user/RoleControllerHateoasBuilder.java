package com.cricketclub.controller.user;

import com.cricketclub.api.resource.user.Role;
import com.cricketclub.api.resource.user.RoleList;
import com.cricketclub.api.resource.user.User;
import com.cricketclub.exception.user.NoSuchRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
class RoleControllerHateoasBuilder {

    private static final String UPDATE_REL = "update-role";

    @Autowired
    private RoleLinksFactory roleLinksFactory;

    List<Link> buildLinksForFindActiveRoles(final RoleList roleList) throws NoSuchRoleException {
        List<Link> links = new ArrayList<Link>();
        links.add(roleLinksFactory.getFindActiveRolesLink(Link.REL_SELF));

        for(Role role : roleList.getRoles() ) {
            roleLinksFactory.getUpdateRoleLink(role.getRoleId(), false, UPDATE_REL);
        }

        return links;
    }
    List<Link> buildLinksForUpdateRole(final Integer roleId, final Boolean selectable) throws NoSuchRoleException {
        List<Link> links = new ArrayList<Link>();
        links.add(roleLinksFactory.getUpdateRoleLink(roleId, selectable, Link.REL_SELF));
        return links;
    }
}

