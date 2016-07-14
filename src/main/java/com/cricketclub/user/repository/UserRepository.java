package com.cricketclub.user.repository;

import com.cricketclub.common.repository.BaseRepository;
import com.cricketclub.user.domain.UserBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<UserBO, Long> {
    Optional<UserBO> findByUsernameAndPassword(final String username, final String password);
    Optional<UserBO> findByUsername(final String username);
}
