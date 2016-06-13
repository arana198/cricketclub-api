package com.cricketclub.committee.controller;

import com.cricketclub.committee.dto.CommitteeRoleList;
import com.cricketclub.committee.exception.NoSuchCommitteeRoleException;
import com.cricketclub.committee.service.CommitteeRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/v1.0/committee-roles")
public class CommitteeRoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitteeRoleController.class);

    private final CommitteeRoleService committeeRoleService;
    private final CommitteeRoleControllerHateoasBuilder committeeRoleControllerHateoasBuilder;

    @Autowired
    public CommitteeRoleController(CommitteeRoleService committeeRoleService, CommitteeRoleControllerHateoasBuilder committeeRoleControllerHateoasBuilder) {
        this.committeeRoleService = committeeRoleService;
        this.committeeRoleControllerHateoasBuilder = committeeRoleControllerHateoasBuilder;
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<CommitteeRoleList> getCommitteeRoles() throws NoSuchCommitteeRoleException{
        LOGGER.info("Getting member roles");
        CommitteeRoleList committeeRoleList = committeeRoleService.getActiveCommitteRole()
                .orElseThrow(() -> new NoSuchCommitteeRoleException());
        committeeRoleList.add(committeeRoleControllerHateoasBuilder.buildLinksForGetCommitteeRoles());

        return new ResponseEntity<CommitteeRoleList>(committeeRoleList, HttpStatus.OK);
    }
}
