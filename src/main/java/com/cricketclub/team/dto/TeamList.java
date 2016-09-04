package com.cricketclub.team.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamList extends BaseDomain {
    private final List<Team> teams;

    @JsonCreator
    public TeamList(@JsonProperty(value = "teams", required = true) final List<Team> teams) {
        this.teams = teams;
    }
}
