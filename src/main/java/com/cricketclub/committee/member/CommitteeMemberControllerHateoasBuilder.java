package com.cricketclub.committee.member;

import com.cricketclub.committee.CommitteeLinksFactory;
import com.cricketclub.committee.member.exception.NoSuchCommitteeMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class CommitteeMemberControllerHateoasBuilder {

    @Autowired
    private CommitteeLinksFactory committeeLinksFactory;

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

