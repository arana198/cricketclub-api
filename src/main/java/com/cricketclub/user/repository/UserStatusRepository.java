package com.cricketclub.user.repository;

import com.cricketclub.user.domain.UserStatusBO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStatusRepository extends JpaRepository<UserStatusBO, Integer>{

    @Cacheable("UserStatusRepository.findById")
    Optional<UserStatusBO> findById(final Integer id);

    @Cacheable("UserStatusRepository.findByName")
    Optional<UserStatusBO> findByName(final UserStatusBO.UserStatus userStatus);
}
