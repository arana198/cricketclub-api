package com.cricketclub.controller.user;

import com.cricketclub.api.hateos.ExtendedLink;
import com.cricketclub.api.resource.user.Role;
import com.cricketclub.exception.user.NoSuchRoleException;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RoleLinksFactory {

    private static final RolesController CONTROLLER = methodOn(RolesController.class);

    public ExtendedLink getFindActiveRolesLink(final String rel) throws NoSuchRoleException  {

        final Link link = linkTo(CONTROLLER.findActiveRoles()).withRel(rel);

        return ExtendedLink.extend(link)
                .withName("roles")
                .withMethods("GET")
                .withDescription("Get all active user roles");
    }

    public ExtendedLink getUpdateRoleLink(final Integer roleId, final Boolean selectable, final String rel) throws NoSuchRoleException {
        final Link link = linkTo(CONTROLLER.updateRole(roleId, selectable)).withRel(rel);

        return ExtendedLink.extend(link)
                .withName("updateRole")
                .withMethods("PUT")
                .withDescription("Update user role type in the system");
    }
}
