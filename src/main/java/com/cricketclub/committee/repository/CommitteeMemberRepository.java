package com.cricketclub.committee.repository;

import com.cricketclub.committee.domain.CommitteeMemberBO;
import com.cricketclub.user.domain.UserBO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommitteeMemberRepository extends JpaRepository<CommitteeMemberBO, Long>{

    @Cacheable("ElectedOfficerRepository.findByYear")
    List<CommitteeMemberBO> findByYear(final Integer year);

    @Cacheable("ElectedOfficerRepository.findByUser")
    List<CommitteeMemberBO> findByUser(final UserBO userBO);

    @Cacheable("ElectedOfficerRepository.findByCommitteeRoleAndYear")
    @Query("select e from CommitteeMemberBO e where e.committeeRole.id = :committeeRoleId and e.year = :year")
    Optional<CommitteeMemberBO> findByCommitteeRoleAndYear(@Param("committeeRoleId") final Integer committeeRoleId, @Param("year") final Integer year);

    @Cacheable("ElectedOfficerRepository.findById")
    Optional<CommitteeMemberBO> findById(final Long id);

}
