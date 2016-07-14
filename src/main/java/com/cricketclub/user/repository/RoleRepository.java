package com.cricketclub.user.repository;

import com.cricketclub.common.repository.BaseRepository;
import com.cricketclub.user.domain.RoleBO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends BaseRepository<RoleBO, Integer> {

    @Cacheable("RoleRepository.findById")
    Optional<RoleBO> findById(final Integer id);

    @Cacheable("RoleRepository.findByName")
    Optional<RoleBO> findByName(final RoleBO.Role role);

    @Cacheable("RoleRepository.findBySelectable")
    List<RoleBO> findBySelectable(final Boolean selectable);
}
