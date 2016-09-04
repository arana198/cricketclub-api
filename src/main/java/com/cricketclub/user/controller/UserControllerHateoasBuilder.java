package com.cricketclub.user.controller;

import com.cricketclub.user.dto.User;
import com.cricketclub.user.exception.NoSuchRoleException;
import com.cricketclub.user.exception.NoSuchUserException;
import com.cricketclub.user.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
class UserControllerHateoasBuilder {

    private final UserLinksFactory userLinksFactory;

    @Autowired
    public UserControllerHateoasBuilder(final UserLinksFactory userLinksFactory) {
        this.userLinksFactory = userLinksFactory;
    }

    List<Link> buildLinksForCreateClubAdminUser(final User user) throws UserAlreadyExistsException, NoSuchRoleException {
        List<Link> links = new ArrayList<Link>();
        links.add(userLinksFactory.getCreateClubAdminUserLink(user, Link.REL_SELF));
        return links;
    }

    List<Link> buildLinksForLogout(final Principal principal) throws NoSuchUserException {
        List<Link> links = new ArrayList<Link>();
        links.add(userLinksFactory.getLogoutLink(principal, Link.REL_SELF));
        return links;
    }
}

