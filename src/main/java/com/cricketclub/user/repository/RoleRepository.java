package com.cricketclub.user.repository;

import com.cricketclub.user.domain.RoleBO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleBO, Integer>{

    @Cacheable("RoleRepository.findById")
    Optional<RoleBO> findById(final Integer id);

    @Cacheable("RoleRepository.findByName")
    Optional<RoleBO> findByName(final RoleBO.Role role);

    @Cacheable("RoleRepository.findBySelectable")
    List<RoleBO> findBySelectable(final Boolean selectable);
}
