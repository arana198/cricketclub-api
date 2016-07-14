package com.cricketclub.team.dto;

import com.cricketclub.common.dto.BaseDomain;

import java.util.List;

public class TeamList extends BaseDomain {

    private final List<Team> teams;

    public TeamList(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> getTeams() {
        return teams;
    }
}
