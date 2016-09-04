package com.cricketclub.profile.repository;

import com.cricketclub.profile.domain.UserProfileBO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfileBO, Long> {
    Optional<UserProfileBO> findByUserId(Long userId);
}
