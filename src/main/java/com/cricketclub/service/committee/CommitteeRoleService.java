package com.cricketclub.service.committee;

import com.cricketclub.domain.committee.CommitteeMemberBO;
import com.cricketclub.domain.committee.CommitteeRoleBO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CommitteeRoleService {
	Optional<CommitteeRoleBO> findById(final Integer id);
	Optional<CommitteeRoleBO> findByName(final CommitteeRoleBO.CommitteeRole name);
	List<CommitteeRoleBO> getActiveCommitteeRoles();
}
