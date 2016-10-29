package com.cricketclub.team.repository;

import com.cricketclub.common.repository.BaseRepository;
import com.cricketclub.team.domain.TeamBO;

import java.util.List;

public interface TeamRepository extends BaseRepository<TeamBO, Long> {
    List<TeamBO> findAllByActive(boolean active);
}
