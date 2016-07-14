package com.cricketclub.team.repository;

import com.cricketclub.common.repository.BaseRepository;
import com.cricketclub.team.domain.SelectedPlayerBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedPlayerRepository extends BaseRepository<SelectedPlayerBO, Long> {
}
