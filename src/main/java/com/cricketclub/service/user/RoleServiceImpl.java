package com.cricketclub.service.user;

import com.cricketclub.domain.user.RoleBO;
import com.cricketclub.repository.user.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class RoleServiceImpl implements RoleService{

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<RoleBO> findActiveRoles() {
		LOGGER.info("Finding all user roles");
		return roleRepository.findBySelectable(true);
	}

	@Override
	public Optional<RoleBO> findById(final Integer id) {
        LOGGER.info("Finding user role by id {}", id);
		return roleRepository.findById(id);
	}

	@Override
	public Optional<RoleBO> findByName(final RoleBO.Role role) {
        LOGGER.info("Finding user role by name {}", role);
		return roleRepository.findByName(role);
	}

	@Override
	public void save(RoleBO roleBO) {
		LOGGER.info("Saving user role by name {}", roleBO.getName());
		roleRepository.save(roleBO);
	}
}
