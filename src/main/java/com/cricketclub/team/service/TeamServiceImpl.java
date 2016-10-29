package com.cricketclub.team.service;

import com.cricketclub.team.domain.TeamBO;
import com.cricketclub.team.dto.Team;
import com.cricketclub.team.dto.TeamList;
import com.cricketclub.team.exception.NoSuchTeamException;
import com.cricketclub.team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamConverter teamConverter;

    @Autowired
    public TeamServiceImpl(final TeamRepository teamRepository, final TeamConverter teamConverter) {
        this.teamRepository = teamRepository;
        this.teamConverter = teamConverter;
    }

    @Override
    public Optional<TeamList> getActiveTeams() {
        List<TeamBO> teamBOList = teamRepository.findAllByActive(true);
        return Optional.of(teamConverter.convert(teamBOList));
    }

    @Override
    public Optional<Team> findById(final long id) throws NoSuchTeamException {
        Team team = teamRepository.findById(id)
                .map(teamConverter::convert)
                .orElseThrow(() -> new NoSuchTeamException(id));
        return Optional.of(team);
    }

    @Override
    public void addTeam(final Team team) {
        TeamBO teamBO = teamConverter.convert(team);
        teamRepository.save(teamBO);
    }

    @Override
    public void updateTeam(final long id, final Team team) throws NoSuchTeamException {
        TeamBO teamBO = teamRepository.findById(id)
                .map(t -> teamConverter.convert(team))
                .orElseThrow(() -> new NoSuchTeamException(id));

        teamBO.setId(id);
        teamRepository.save(teamBO);
    }

    @Override
    public void inactivateTeam(final long id) throws NoSuchTeamException {
        TeamBO team = teamRepository.findById(id)
                .orElseThrow(() -> new NoSuchTeamException(id));

        team.setActive(false);
        teamRepository.save(team);
    }
}
