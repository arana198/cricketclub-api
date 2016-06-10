package com.cricketclub.committee.role;

import com.cricketclub.committee.role.dto.CommitteeRole;
import com.cricketclub.committee.role.dto.CommitteeRoleList;
import com.cricketclub.committee.role.domain.CommitteeRoleBO;
import com.cricketclub.committee.role.repository.CommitteeRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class CommitteeRoleServiceImpl implements CommitteeRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitteeRoleServiceImpl.class);

    @Autowired
    private CommitteeRoleRepository committeeRoleRepository;

    @Autowired
    private CommitteeRoleConverter committeeRoleConverter;

    @Override
    public Optional<CommitteeRoleList> getActiveCommitteRole() {
        List<CommitteeRoleBO> committeeRoleBOList = committeeRoleRepository.findByVisible(true);
        if(committeeRoleBOList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(committeeRoleConverter.convert(committeeRoleBOList));
    }

    @Override
    public Optional<CommitteeRole> findById(final Integer id) {
        LOGGER.info("Finding member role by id {}", id);
        return committeeRoleRepository.findById(id)
                .map(cr -> committeeRoleConverter.convert(cr));
    }
}
