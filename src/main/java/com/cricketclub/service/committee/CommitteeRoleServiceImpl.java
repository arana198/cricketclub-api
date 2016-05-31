package com.cricketclub.service.committee;

import com.cricketclub.domain.committee.CommitteeRoleBO;
import com.cricketclub.repository.committee.CommitteeRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class CommitteeRoleServiceImpl implements CommitteeRoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommitteeRoleServiceImpl.class);

	@Autowired
	private CommitteeRoleRepository committeeRoleRepository;

	@Override
	public Optional<CommitteeRoleBO> findById(final Integer id) {
        LOGGER.info("Finding committee role by id {}", id);
		return committeeRoleRepository.findById(id);
	}

	@Override
	public Optional<CommitteeRoleBO> findByName(final CommitteeRoleBO.CommitteeRole name) {
        LOGGER.info("Finding committee role by name {}", name);
		return committeeRoleRepository.findByName(name);
	}

	@Override
	public List<CommitteeRoleBO> getActiveCommitteeRoles() {
        LOGGER.info("Finding all committee roles");
		return committeeRoleRepository.findByVisible(true);
	}
}
