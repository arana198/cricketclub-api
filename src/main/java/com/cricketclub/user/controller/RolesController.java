package com.cricketclub.user.controller;

import com.cricketclub.user.dto.RoleList;
import com.cricketclub.user.dto.User;
import com.cricketclub.user.exception.NoSuchRoleException;
import com.cricketclub.user.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@ExposesResourceFor(User.class)
@RequestMapping(value = "/v1.0/roles")
public class RolesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RolesController.class);

    private final RoleService roleService;
    private final RoleControllerHateoasBuilder roleControllerHateoasBuilder;

    @Autowired
    public RolesController(final RoleService roleService, final RoleControllerHateoasBuilder roleControllerHateoasBuilder) {
        this.roleService = roleService;
        this.roleControllerHateoasBuilder = roleControllerHateoasBuilder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RoleList> findActiveRoles() throws NoSuchRoleException {
        RoleList response = roleService.findActiveRoles()
                .orElseThrow(() -> new NoSuchRoleException());

        response.add(roleControllerHateoasBuilder.buildLinksForFindActiveRoles(response));
        return new ResponseEntity<RoleList>(response, org.springframework.http.HttpStatus.OK);
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.PUT)
    public ResponseEntity<ResourceSupport> updateRole(@PathVariable final int roleId, @RequestParam("selectable") final boolean selectable) throws NoSuchRoleException {
        LOGGER.info("Updating role {} with sectable: {}", roleId, selectable);
        roleService.updateRole(roleId, selectable);
        ResourceSupport response = new ResourceSupport();
        response.add(roleControllerHateoasBuilder.buildLinksForUpdateRole(roleId, selectable));
        return new ResponseEntity<ResourceSupport>(response, HttpStatus.NO_CONTENT);
    }
}
