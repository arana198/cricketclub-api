package com.cricketclub.committee.repository;

import com.cricketclub.committee.domain.CommitteeRoleBO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommitteeRoleRepository extends JpaRepository<CommitteeRoleBO, Integer>{

    @Cacheable("CommitteeRoleRepository.findById")
    Optional<CommitteeRoleBO> findById(final Integer id);

    @Cacheable("CommitteeRoleRepository.findByName")
    Optional<CommitteeRoleBO> findByName(final CommitteeRoleBO.CommitteeRole name);

    @Cacheable("CommitteeRoleRepository.findByVisible")
    List<CommitteeRoleBO> findByVisible(final Boolean visible);
}
