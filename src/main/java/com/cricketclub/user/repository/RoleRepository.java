package com.cricketclub.user.repository;

import com.cricketclub.common.repository.BaseRepository;
import com.cricketclub.user.domain.RoleBO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends BaseRepository<RoleBO, Integer> {

    @Cacheable("RoleRepository.findById")
    Optional<RoleBO> findById(final Integer id);

    @Cacheable("RoleRepository.findByName")
    Optional<RoleBO> findByName(final String role);

    @Cacheable("RoleRepository.findBySelectable")
    List<RoleBO> findBySelectable(final Boolean selectable);
}
