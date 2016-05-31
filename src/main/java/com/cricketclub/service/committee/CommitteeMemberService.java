package com.cricketclub.service.committee;

import com.cricketclub.domain.committee.CommitteeMemberBO;
import com.cricketclub.domain.user.UserBO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CommitteeMemberService {
    Optional<CommitteeMemberBO> findById(final Long id);
	List<CommitteeMemberBO> findByYear(final Integer year);
	List<CommitteeMemberBO> findByUser(final UserBO userBO);
    Optional<CommitteeMemberBO> findByCommitteeRoleAndYear(final Integer committeeRoleId, final Integer year);
    void save(final CommitteeMemberBO committeeMemberBO);
    void remove(final CommitteeMemberBO committeeMemberBO);
}
