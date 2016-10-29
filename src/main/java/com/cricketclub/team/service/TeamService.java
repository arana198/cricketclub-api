package com.cricketclub.team.service;

import com.cricketclub.team.dto.Team;
import com.cricketclub.team.dto.TeamList;
import com.cricketclub.team.exception.NoSuchTeamException;

import java.util.Optional;

public interface TeamService {
    Optional<TeamList> getActiveTeams();

    Optional<Team> findById(long id) throws NoSuchTeamException;

    void addTeam(Team team);

    void updateTeam(long id, Team team) throws NoSuchTeamException;

    void inactivateTeam(long id) throws NoSuchTeamException;
}
