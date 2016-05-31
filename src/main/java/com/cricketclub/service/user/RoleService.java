package com.cricketclub.service.user;

import com.cricketclub.domain.user.RoleBO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface RoleService {
	List<RoleBO> findActiveRoles();
	Optional<RoleBO> findById(final Integer id);
	Optional<RoleBO> findByName(final RoleBO.Role role);
	void save(final RoleBO roleBO);
}
