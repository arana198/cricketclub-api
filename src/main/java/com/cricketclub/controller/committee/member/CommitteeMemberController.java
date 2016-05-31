package com.cricketclub.controller.committee.member;

import com.cricketclub.api.resource.committee.CommitteeMember;
import com.cricketclub.api.resource.committee.CommitteeMemberList;
import com.cricketclub.exception.BadRequestException;
import com.cricketclub.exception.committee.CommitteeMemberAlreadyExistsException;
import com.cricketclub.exception.committee.NoSuchCommitteeMemberException;
import com.cricketclub.exception.committee.NoSuchCommitteeRoleException;
import com.cricketclub.exception.user.NoSuchUserException;
import com.cricketclub.facade.committee.CommitteeMemberFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
@RequestMapping(value="/v1.0/committees")
public class CommitteeMemberController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitteeMemberController.class);

    @Autowired
    private CommitteeMemberFacade committeeMemberFacade;

    @Autowired
    private CommitteeMemberControllerHateoasBuilder committeeMemberControllerHateoasBuilder;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<CommitteeMemberList> getLatestCommittee() throws NoSuchCommitteeMemberException {
        LOGGER.info("Getting committee members");
        CommitteeMemberList response = committeeMemberFacade.getLatestCommitteeMembers()
                .orElseThrow(() -> new NoSuchCommitteeMemberException());

        response.add(committeeMemberControllerHateoasBuilder.buildLinksForGetLatestCommittee());
        return new ResponseEntity<CommitteeMemberList>(response, org.springframework.http.HttpStatus.OK);
    }

    @RolesAllowed({"ROLE_CLUB_ADMIN"})
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<ResourceSupport> addCommitteeMember(@RequestBody @Valid CommitteeMember committeeMember, BindingResult bindingResult) throws NoSuchCommitteeRoleException, NoSuchUserException, CommitteeMemberAlreadyExistsException, NoSuchCommitteeMemberException {
        LOGGER.info("Saving committee members");
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid user object", bindingResult);
        }

        committeeMemberFacade.addCommitteeMember(committeeMember);
        ResourceSupport response = new ResourceSupport();
        response.add(committeeMemberControllerHateoasBuilder.buildLinksForAddCommitteeMember());
        return new ResponseEntity<ResourceSupport>(response, HttpStatus.CREATED);
    }

    @RolesAllowed({"ROLE_CLUB_ADMIN"})
    @RequestMapping(value = "/{committeeId}", method=RequestMethod.PUT)
    public ResponseEntity<ResourceSupport> updateCommitteeMember(@PathVariable Long committeeId, @RequestBody @Valid CommitteeMember committeeMember, BindingResult bindingResult) throws NoSuchUserException, NoSuchCommitteeMemberException, NoSuchCommitteeRoleException, CommitteeMemberAlreadyExistsException {
        LOGGER.info("Updating committee members with id {}", committeeId);
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid user object", bindingResult);
        }

        committeeMemberFacade.updateCommitteeMember(committeeId, committeeMember);
        ResourceSupport response = new ResourceSupport();
        response.add(committeeMemberControllerHateoasBuilder.buildLinksForUpdateCommitteeMember());
        return new ResponseEntity<ResourceSupport>(response, HttpStatus.NO_CONTENT);
    }

    @RolesAllowed({"ROLE_CLUB_ADMIN"})
    @RequestMapping(value = "/{committeeId}", method=RequestMethod.DELETE)
    public ResponseEntity<ResourceSupport> deleteCommitteeMember(@PathVariable Long committeeId) throws NoSuchCommitteeMemberException {
        LOGGER.info("Deleting committee members with id {}", committeeId);
        committeeMemberFacade.deleteCommitteeMember(committeeId);
        ResourceSupport response = new ResourceSupport();
        response.add(committeeMemberControllerHateoasBuilder.buildLinksForDeleteCommitteeMember());
        return new ResponseEntity<ResourceSupport>(response, org.springframework.http.HttpStatus.OK);
    }
}
