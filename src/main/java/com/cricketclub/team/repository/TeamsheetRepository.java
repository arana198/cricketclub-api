package com.cricketclub.team.repository;

import com.cricketclub.common.repository.BaseRepository;
import com.cricketclub.team.domain.SelectedPlayerBO;
import com.cricketclub.team.domain.TeamsheetBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamsheetRepository extends BaseRepository<TeamsheetBO, Long> {
}
