package com.cricketclub.controller.committee.role;

import com.cricketclub.api.resource.committee.CommitteeMember;
import com.cricketclub.api.resource.committee.CommitteeRole;
import com.cricketclub.api.resource.committee.CommitteeRoleList;
import com.cricketclub.exception.committee.NoSuchCommitteeRoleException;
import com.cricketclub.facade.committee.CommitteeRoleFacade;
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

    @Autowired
    private CommitteeRoleFacade committeeRoleFacade;

    @Autowired
    private CommitteeRoleControllerHateoasBuilder committeeRoleControllerHateoasBuilder;


    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<CommitteeRoleList> getCommitteeRoles() throws NoSuchCommitteeRoleException{
        LOGGER.info("Getting committee roles");
        CommitteeRoleList committeeRoleList = committeeRoleFacade.getActiveCommitteRole()
                .orElseThrow(() -> new NoSuchCommitteeRoleException());
        committeeRoleList.add(committeeRoleControllerHateoasBuilder.buildLinksForGetCommitteeRoles());

        return new ResponseEntity<CommitteeRoleList>(committeeRoleList, HttpStatus.OK);
    }
}