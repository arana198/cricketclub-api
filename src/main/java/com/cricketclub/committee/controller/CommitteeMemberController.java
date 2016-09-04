package com.cricketclub.committee.controller;

import com.cricketclub.committee.dto.CommitteeMember;
import com.cricketclub.committee.dto.CommitteeMemberList;
import com.cricketclub.common.exception.BadRequestException;
import com.cricketclub.committee.exception.CommitteeMemberAlreadyExistsException;
import com.cricketclub.committee.exception.NoSuchCommitteeMemberException;
import com.cricketclub.committee.exception.NoSuchCommitteeRoleException;
import com.cricketclub.user.exception.NoSuchUserException;
import com.cricketclub.committee.service.CommitteeMemberService;
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

    private final CommitteeMemberService committeeMemberService;
    private final CommitteeMemberControllerHateoasBuilder committeeMemberControllerHateoasBuilder;

    @Autowired
    public CommitteeMemberController(final CommitteeMemberService committeeMemberService,
                                     final CommitteeMemberControllerHateoasBuilder committeeMemberControllerHateoasBuilder) {
        this.committeeMemberService = committeeMemberService;
        this.committeeMemberControllerHateoasBuilder = committeeMemberControllerHateoasBuilder;
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<CommitteeMemberList> getLatestCommittee() throws NoSuchCommitteeMemberException {
        LOGGER.info("Getting member members");
        CommitteeMemberList response = committeeMemberService.getLatestCommitteeMembers()
                .orElseThrow(() -> new NoSuchCommitteeMemberException());

        response.add(committeeMemberControllerHateoasBuilder.buildLinksForGetLatestCommittee());
        return new ResponseEntity<CommitteeMemberList>(response, org.springframework.http.HttpStatus.OK);
    }

    @RolesAllowed({"ROLE_CLUB_ADMIN"})
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<ResourceSupport> addCommitteeMember(@RequestBody @Valid final CommitteeMember committeeMember,
                                                              final BindingResult bindingResult)
            throws NoSuchCommitteeRoleException, NoSuchUserException, CommitteeMemberAlreadyExistsException, NoSuchCommitteeMemberException {
        LOGGER.info("Saving member members");
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid service object", bindingResult);
        }

        committeeMemberService.addCommitteeMember(committeeMember);
        ResourceSupport response = new ResourceSupport();
        response.add(committeeMemberControllerHateoasBuilder.buildLinksForAddCommitteeMember());
        return new ResponseEntity<ResourceSupport>(response, HttpStatus.CREATED);
    }

    @RolesAllowed({"ROLE_CLUB_ADMIN"})
    @RequestMapping(value = "/{committeeId}", method=RequestMethod.PUT)
    public ResponseEntity<ResourceSupport> updateCommitteeMember(@PathVariable final long committeeId,
                                                                 @RequestBody @Valid final CommitteeMember committeeMember,
                                                                 final BindingResult bindingResult)
            throws NoSuchUserException, NoSuchCommitteeMemberException, NoSuchCommitteeRoleException, CommitteeMemberAlreadyExistsException {
        LOGGER.info("Updating member members with id {}", committeeId);
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid service object", bindingResult);
        }

        committeeMemberService.updateCommitteeMember(committeeId, committeeMember);
        ResourceSupport response = new ResourceSupport();
        response.add(committeeMemberControllerHateoasBuilder.buildLinksForUpdateCommitteeMember());
        return new ResponseEntity<ResourceSupport>(response, HttpStatus.NO_CONTENT);
    }

    @RolesAllowed({"ROLE_CLUB_ADMIN"})
    @RequestMapping(value = "/{committeeId}", method=RequestMethod.DELETE)
    public ResponseEntity<ResourceSupport> deleteCommitteeMember(@PathVariable final long committeeId) throws NoSuchCommitteeMemberException {
        LOGGER.info("Deleting member members with id {}", committeeId);
        committeeMemberService.deleteCommitteeMember(committeeId);
        ResourceSupport response = new ResourceSupport();
        response.add(committeeMemberControllerHateoasBuilder.buildLinksForDeleteCommitteeMember());
        return new ResponseEntity<ResourceSupport>(response, org.springframework.http.HttpStatus.OK);
    }
}
