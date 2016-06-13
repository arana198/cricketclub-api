package com.cricketclub.committee.controller;

import com.cricketclub.committee.CommitteeLinksFactory;
import com.cricketclub.committee.exception.NoSuchCommitteeRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class CommitteeRoleControllerHateoasBuilder {

    private final CommitteeLinksFactory committeeLinksFactory;

    @Autowired
    public CommitteeRoleControllerHateoasBuilder(CommitteeLinksFactory committeeLinksFactory) {
        this.committeeLinksFactory = committeeLinksFactory;
    }

    List<Link> buildLinksForGetCommitteeRoles() throws NoSuchCommitteeRoleException {
        List<Link> links = new ArrayList<Link>();
        links.add(committeeLinksFactory.getCommitteeRoleLink(Link.REL_SELF));
        return links;
    }
}

