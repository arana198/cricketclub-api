package com.cricketclub.controller.user;

import com.cricketclub.api.resource.user.RoleList;
import com.cricketclub.api.resource.user.User;
import com.cricketclub.exception.user.NoSuchRoleException;
import com.cricketclub.facade.user.RoleFacade;
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
    private RoleFacade roleFacade;

    @Autowired
    private RoleControllerHateoasBuilder roleControllerHateoasBuilder;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<RoleList> findActiveRoles() throws NoSuchRoleException {
        RoleList response = roleFacade.findActiveRoles()
                .orElseThrow(() -> new NoSuchRoleException());

        response.add(roleControllerHateoasBuilder.buildLinksForFindActiveRoles(response));
        return new ResponseEntity<RoleList>(response, org.springframework.http.HttpStatus.OK);
    }

    @RequestMapping(value = "/{roleId}", method=RequestMethod.PUT)
    public ResponseEntity<ResourceSupport> updateRole(@PathVariable final Integer roleId, @RequestParam("selectable") final Boolean selectable) throws NoSuchRoleException {
        LOGGER.info("Updating role {} with sectable: {}", roleId, selectable);
        roleFacade.updateRole(roleId, selectable);
        ResourceSupport response = new ResourceSupport();
        response.add(roleControllerHateoasBuilder.buildLinksForUpdateRole(roleId, selectable));
        return new ResponseEntity<ResourceSupport>(response, HttpStatus.NO_CONTENT);
    }
}
