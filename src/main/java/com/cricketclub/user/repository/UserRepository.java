package com.cricketclub.user.repository;

import com.cricketclub.user.domain.UserBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserBO, Long>{
    Optional<UserBO> findByUsernameAndPassword(final String username, final String password);
    Optional<UserBO> findById(final Long id);
    Optional<UserBO> findByUsername(final String username);
}
