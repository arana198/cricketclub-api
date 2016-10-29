package com.cricketclub.user.controller;

import com.cricketclub.common.hateos.ExtendedLink;
import com.cricketclub.user.dto.User;
import com.cricketclub.user.exception.NoSuchRoleException;
import com.cricketclub.user.exception.NoSuchUserException;
import com.cricketclub.user.exception.UserAlreadyExistsException;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.security.Principal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserLinksFactory {

    private static final UserController CONTROLLER = methodOn(UserController.class);

    public ExtendedLink getCreateClubAdminUserLink(final User user, final String rel) throws UserAlreadyExistsException, NoSuchRoleException {
        final Link link = linkTo(CONTROLLER.createClubAdminUser(user, null)).withRel(rel);

        return ExtendedLink.extend(link)
                .withName("clubAdminUser")
                .withMethods("POST")
                .withDescription("Register a club admin service");
    }

    public ExtendedLink getLogoutLink(final Principal principal, final String rel) throws NoSuchUserException {
        final Link link = linkTo(CONTROLLER.logout(principal)).withRel(rel);

        return ExtendedLink.extend(link)
                .withName("logout")
                .withMethods("DELETE")
                .withDescription("Logout a service from system");
    }
}
