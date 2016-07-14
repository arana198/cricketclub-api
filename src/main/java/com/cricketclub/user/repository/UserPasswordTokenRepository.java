package com.cricketclub.user.repository;

import com.cricketclub.common.repository.BaseRepository;
import com.cricketclub.user.domain.UserPasswordTokenBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserPasswordTokenRepository extends BaseRepository<UserPasswordTokenBO, Long> {

    @Query("select upt from UserPasswordTokenBO upt where upt.user.id = :userId and upt.token = :token")
    Optional<UserPasswordTokenBO> findByUserIdAndToken(@Param("userId") final Long userId, @Param("token") final String token);

    @Query("select upt from UserPasswordTokenBO upt where upt.user.id = :userId")
    Optional<UserPasswordTokenBO> findByUserId(@Param("userId") final Long userId);
}
