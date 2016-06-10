package com.cricketclub.user.controller;

import com.cricketclub.user.dto.RoleList;
import com.cricketclub.user.dto.User;
import com.cricketclub.user.exception.NoSuchRoleException;
import com.cricketclub.user.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ExposesResourceFor(User.class)
@RequestMapping(value="/v1.0/roles")
public class RolesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RolesController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleControllerHateoasBuilder roleControllerHateoasBuilder;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<RoleList> findActiveRoles() throws NoSuchRoleException {
        RoleList response = roleService.findActiveRoles()
                .orElseThrow(() -> new NoSuchRoleException());

        response.add(roleControllerHateoasBuilder.buildLinksForFindActiveRoles(response));
        return new ResponseEntity<RoleList>(response, org.springframework.http.HttpStatus.OK);
    }

    @RequestMapping(value = "/{roleId}", method=RequestMethod.PUT)
    public ResponseEntity<ResourceSupport> updateRole(@PathVariable final Integer roleId, @RequestParam("selectable") final Boolean selectable) throws NoSuchRoleException {
        LOGGER.info("Updating role {} with sectable: {}", roleId, selectable);
        roleService.updateRole(roleId, selectable);
        ResourceSupport response = new ResourceSupport();
        response.add(roleControllerHateoasBuilder.buildLinksForUpdateRole(roleId, selectable));
        return new ResponseEntity<ResourceSupport>(response, HttpStatus.NO_CONTENT);
    }
}
