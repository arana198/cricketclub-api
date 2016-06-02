package com.cricketclub.committee;

import com.cricketclub.common.hateos.ExtendedLink;
import com.cricketclub.committee.member.controller.CommitteeMemberController;
import com.cricketclub.committee.role.controller.CommitteeRoleController;
import com.cricketclub.committee.member.exception.NoSuchCommitteeMemberException;
import com.cricketclub.committee.role.exception.NoSuchCommitteeRoleException;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CommitteeLinksFactory {

    private static final CommitteeRoleController COMMITTEE_ROLE_CONTROLLER = methodOn(CommitteeRoleController.class);
    private static final CommitteeMemberController COMMITTEE_MEMBER_CONTROLLER = methodOn(CommitteeMemberController.class);

    public ExtendedLink getCommitteeRoleLink(final String rel) throws NoSuchCommitteeRoleException {
        final Link link = linkTo(COMMITTEE_ROLE_CONTROLLER.getCommitteeRoles()).withRel(rel);

        return ExtendedLink.extend(link)
                .withName("committeeRole")
                .withMethods("GET")
                .withDescription("Get committee roles from the system");
    }

    public ExtendedLink getCommitteeMemberLink(final String rel) throws NoSuchCommitteeMemberException {
        final Link link = linkTo(COMMITTEE_MEMBER_CONTROLLER.getLatestCommittee()).withRel(rel);

        return ExtendedLink.extend(link)
                .withName("committeeMembers")
                .withMethods("GET, POST, UPDATE, DELETE")
                .withDescription("Get, Add and Update committee members");
    }
}
