package com.cricketclub.user.controller;

import com.cricketclub.common.exception.BadRequestException;
import com.cricketclub.user.domain.RoleBO;
import com.cricketclub.user.dto.Role;
import com.cricketclub.user.dto.User;
import com.cricketclub.user.exception.NoSuchRoleException;
import com.cricketclub.user.exception.NoSuchUserException;
import com.cricketclub.user.exception.UserAlreadyExistsException;
import com.cricketclub.user.service.UserService;
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

    private final UserService userService;
    private final UserControllerHateoasBuilder userControllerHateoasBuilder;

    @Autowired
    public UserController(final UserService userService, final UserControllerHateoasBuilder userControllerHateoasBuilder) {
        this.userService = userService;
        this.userControllerHateoasBuilder = userControllerHateoasBuilder;
    }

    @RequestMapping(value = "/logout", method=RequestMethod.POST)
    public ResponseEntity<ResourceSupport> logout(Principal principal) throws NoSuchUserException {
        userService.logout(principal);
        ResourceSupport response = new ResourceSupport();
        response.add(userControllerHateoasBuilder.buildLinksForLogout(principal));
        return new ResponseEntity<ResourceSupport>(response, org.springframework.http.HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<ResourceSupport> createUser(@RequestBody @Valid final User user, final BindingResult bindingResult) throws UserAlreadyExistsException, NoSuchRoleException {
        LOGGER.info("Creating service with username {}", user.getUsername());
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid service object", bindingResult);
        }

        userService.createUser(user, Role.UserRole.ROLE_USER);

        ResourceSupport response = new ResourceSupport();
        response.add(userControllerHateoasBuilder.buildLinksForCreateClubAdminUser(user));
        return new ResponseEntity<ResourceSupport>(response, org.springframework.http.HttpStatus.CREATED);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @RequestMapping(value = "/admin", method=RequestMethod.POST)
    public ResponseEntity<ResourceSupport> createClubAdminUser(@RequestBody @Valid final User user, final BindingResult bindingResult) throws UserAlreadyExistsException, NoSuchRoleException {
        LOGGER.info("Creating Club admin with username {}", user.getUsername());
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid service object", bindingResult);
        }

        userService.createUser(user, Role.UserRole.ROLE_CLUB_ADMIN);

        ResourceSupport response = new ResourceSupport();
        response.add(userControllerHateoasBuilder.buildLinksForCreateClubAdminUser(user));
        return new ResponseEntity<ResourceSupport>(response, org.springframework.http.HttpStatus.CREATED);
    }
}
