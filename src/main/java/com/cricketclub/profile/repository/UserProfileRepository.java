package com.cricketclub.profile.repository;

import com.cricketclub.profile.domain.UserProfileBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileBO, Long> {
}
