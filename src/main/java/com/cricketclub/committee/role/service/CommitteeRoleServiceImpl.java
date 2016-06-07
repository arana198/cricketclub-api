package com.cricketclub.committee.role.service;

import com.cricketclub.committee.role.dto.CommitteeRole;
import com.cricketclub.committee.role.dto.CommitteeRoleList;
import com.cricketclub.committee.role.domain.CommitteeRoleBO;
import com.cricketclub.common.mapper.Converter;
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
    private Converter<CommitteeRoleBO, CommitteeRole, CommitteeRoleList> converter;

    @Override
    public Optional<CommitteeRoleList> getActiveCommitteRole() {
        List<CommitteeRoleBO> committeeRoleBOList = committeeRoleRepository.findByVisible(true);
        if(committeeRoleBOList.isEmpty()) {
            return Optional.empty();
        }

        CommitteeRoleList committeeRoleList = converter.transformToList(converter.transform(committeeRoleBOList));
        return Optional.of(committeeRoleList);
    }

    @Override
    public Optional<CommitteeRole> findById(final Integer id) {
        LOGGER.info("Finding committee role by id {}", id);
        Optional<CommitteeRoleBO> committeeRoleBOOptional = committeeRoleRepository.findById(id);
        if(!committeeRoleBOOptional.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(converter.transform(committeeRoleBOOptional.get()));
    }
}
