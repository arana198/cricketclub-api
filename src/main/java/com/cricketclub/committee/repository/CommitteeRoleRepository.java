package com.cricketclub.committee.repository;

import com.cricketclub.committee.domain.CommitteeRoleBO;
import com.cricketclub.common.repository.BaseRepository;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

public interface CommitteeRoleRepository extends BaseRepository<CommitteeRoleBO, Integer> {

    @Cacheable("CommitteeRoleRepository.findById")
    Optional<CommitteeRoleBO> findById(final Integer id);

    @Cacheable("CommitteeRoleRepository.findByName")
    Optional<CommitteeRoleBO> findByName(final String name);

    @Cacheable("CommitteeRoleRepository.findByVisible")
    List<CommitteeRoleBO> findByVisible(final Boolean visible);
}
