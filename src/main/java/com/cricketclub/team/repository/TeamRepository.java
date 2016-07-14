package com.cricketclub.team.repository;

import com.cricketclub.common.repository.BaseRepository;
import com.cricketclub.team.domain.TeamBO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends BaseRepository<TeamBO, Long> {
    List<TeamBO> findAllByActive(boolean active);
}
