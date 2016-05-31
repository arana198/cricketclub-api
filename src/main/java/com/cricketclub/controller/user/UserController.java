package com.cricketclub.controller.user;

import com.cricketclub.api.resource.user.User;
import com.cricketclub.domain.user.RoleBO;
import com.cricketclub.exception.BadRequestException;
import com.cricketclub.exception.user.NoSuchUserException;
import com.cricketclub.exception.user.UserAlreadyExistsException;
import com.cricketclub.facade.user.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value="/v1.0/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private UserControllerHateoasBuilder userControllerHateoasBuilder;

    @RequestMapping(value = "/logout", method=RequestMethod.POST)
    public ResponseEntity<ResourceSupport> logout(Principal principal) throws NoSuchUserException {
        userFacade.logout(principal);
        ResourceSupport response = new ResourceSupport();
        response.add(userControllerHateoasBuilder.buildLinksForLogout(principal));
        return new ResponseEntity<ResourceSupport>(response, org.springframework.http.HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<ResourceSupport> createUser(@RequestBody @Valid final User user, final BindingResult bindingResult) throws UserAlreadyExistsException {
        LOGGER.info("Creating user with username {}", user.getUsername());
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid user object", bindingResult);
        }

        userFacade.createUser(user, RoleBO.Role.ROLE_USER);

        ResourceSupport response = new ResourceSupport();
        response.add(userControllerHateoasBuilder.buildLinksForCreateClubAdminUser(user));
        return new ResponseEntity<ResourceSupport>(response, org.springframework.http.HttpStatus.CREATED);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @RequestMapping(value = "/admin", method=RequestMethod.POST)
    public ResponseEntity<ResourceSupport> createClubAdminUser(@RequestBody @Valid final User user, final BindingResult bindingResult) throws UserAlreadyExistsException {
        LOGGER.info("Creating Club admin with username {}", user.getUsername());
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid user object", bindingResult);
        }

        userFacade.createUser(user, RoleBO.Role.ROLE_CLUB_ADMIN);

        ResourceSupport response = new ResourceSupport();
        response.add(userControllerHateoasBuilder.buildLinksForCreateClubAdminUser(user));
        return new ResponseEntity<ResourceSupport>(response, org.springframework.http.HttpStatus.CREATED);
    }
}
