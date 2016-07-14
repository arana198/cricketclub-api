package com.cricketclub.committee.service;

import com.cricketclub.committee.dto.CommitteeRole;
import com.cricketclub.committee.dto.CommitteeRoleList;
import com.cricketclub.committee.domain.CommitteeRoleBO;
import com.cricketclub.committee.repository.CommitteeRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class CommitteeRoleServiceImpl extends CommitteeRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitteeRoleServiceImpl.class);

    private final CommitteeRoleRepository committeeRoleRepository;
    private final CommitteeRoleConverter committeeRoleConverter;

    @Autowired
    public CommitteeRoleServiceImpl(CommitteeRoleRepository committeeRoleRepository, CommitteeRoleConverter committeeRoleConverter) {
        this.committeeRoleRepository = committeeRoleRepository;
        this.committeeRoleConverter = committeeRoleConverter;
    }

    @Override
    public Optional<CommitteeRoleList> getActiveCommitteRole() {
        List<CommitteeRoleBO> committeeRoleBOList = committeeRoleRepository.findByVisible(true);
        if(committeeRoleBOList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(committeeRoleConverter.convert(committeeRoleBOList));
    }

    @Override
    Optional<CommitteeRoleBO> findById(final int id) {
        return committeeRoleRepository.findById(id);
    }
}
