package com.cricketclub.controller.user;

import com.cricketclub.api.resource.user.User;
import com.cricketclub.exception.user.NoSuchUserException;
import com.cricketclub.exception.user.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
class UserControllerHateoasBuilder {

    @Autowired
    private UserLinksFactory userLinksFactory;

    List<Link> buildLinksForCreateClubAdminUser(final User user) throws UserAlreadyExistsException {
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

