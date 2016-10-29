package com.cricketclub.user.controller;

import com.cricketclub.common.hateos.ExtendedLink;
import com.cricketclub.user.exception.NoSuchRoleException;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RoleLinksFactory {

    private static final RolesController CONTROLLER = methodOn(RolesController.class);

    public ExtendedLink getFindActiveRolesLink(final String rel) throws NoSuchRoleException {

        final Link link = linkTo(CONTROLLER.findActiveRoles()).withRel(rel);

        return ExtendedLink.extend(link)
                .withName("roles")
                .withMethods("GET")
                .withDescription("Get all active service roles");
    }

    public ExtendedLink getUpdateRoleLink(final int roleId, final boolean selectable, final String rel) throws NoSuchRoleException {
        final Link link = linkTo(CONTROLLER.updateRole(roleId, selectable)).withRel(rel);

        return ExtendedLink.extend(link)
                .withName("updateRole")
                .withMethods("PUT")
                .withDescription("Update service role type in the system");
    }
}
