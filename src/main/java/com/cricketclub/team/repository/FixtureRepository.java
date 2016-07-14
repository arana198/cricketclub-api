package com.cricketclub.team.repository;

import com.cricketclub.common.repository.BaseRepository;
import com.cricketclub.team.domain.FixtureBO;
import com.cricketclub.team.domain.TeamBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixtureRepository extends BaseRepository<FixtureBO, Long> {
}
