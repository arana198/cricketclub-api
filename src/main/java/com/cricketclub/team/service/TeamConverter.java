package com.cricketclub.team.service;

import com.cricketclub.common.converter.Converter;
import com.cricketclub.team.domain.TeamBO;
import com.cricketclub.team.dto.Team;
import com.cricketclub.team.dto.TeamList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class TeamConverter implements Converter<TeamBO, Team> {
    @Override
    public Team convert(final TeamBO source) {
        return new Team(source.getId(),
                source.getName(),
                source.getDescription(),
                source.getImageUrl(),
                source.isActive());
    }

    @Override
    public TeamBO convert(final Team source) {
        TeamBO teamBO = new TeamBO();
        teamBO.setId(source.getTeamId());
        teamBO.setName(source.getName());
        teamBO.setDescription(source.getDescription());
        teamBO.setActive(true);
        return teamBO;
    }

    public TeamList convert(final List<TeamBO> source) {
        return new TeamList(source.stream()
                .map(this::convert)
                .collect(Collectors.toList()));
    }
}
