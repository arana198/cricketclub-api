package com.cricketclub.committee.controller;

import com.cricketclub.committee.CommitteeLinksFactory;
import com.cricketclub.committee.exception.NoSuchCommitteeMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class CommitteeMemberControllerHateoasBuilder {

    private final CommitteeLinksFactory committeeLinksFactory;

    @Autowired
    public CommitteeMemberControllerHateoasBuilder(final CommitteeLinksFactory committeeLinksFactory) {
        this.committeeLinksFactory = committeeLinksFactory;
    }

    List<Link> buildLinksForGetLatestCommittee() throws NoSuchCommitteeMemberException {
        List<Link> links = new ArrayList<Link>();
        links.add(committeeLinksFactory.getCommitteeMemberLink(Link.REL_SELF));
        return links;
    }

    List<Link> buildLinksForAddCommitteeMember() throws NoSuchCommitteeMemberException {
        List<Link> links = new ArrayList<Link>();
        links.add(committeeLinksFactory.getCommitteeMemberLink(Link.REL_SELF));
        return links;
    }

    List<Link> buildLinksForUpdateCommitteeMember() throws NoSuchCommitteeMemberException {
        List<Link> links = new ArrayList<Link>();
        links.add(committeeLinksFactory.getCommitteeMemberLink(Link.REL_SELF));
        return links;
    }

    List<Link> buildLinksForDeleteCommitteeMember() throws NoSuchCommitteeMemberException {
        List<Link> links = new ArrayList<Link>();
        links.add(committeeLinksFactory.getCommitteeMemberLink(Link.REL_SELF));
        return links;
    }
}

