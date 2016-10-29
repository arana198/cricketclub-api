package com.cricketclub.committee.service;

import com.cricketclub.committee.domain.CommitteeRoleBO;
import com.cricketclub.committee.dto.CommitteeRole;
import com.cricketclub.committee.dto.CommitteeRoleList;
import com.cricketclub.common.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class CommitteeRoleConverter implements Converter<CommitteeRoleBO, CommitteeRole> {

    @Override
    public CommitteeRole convert(final CommitteeRoleBO committeeRoleBO) {
        return new CommitteeRole(
                committeeRoleBO.getId(),
                CommitteeRole.Role.getFromString(committeeRoleBO.getName()),
                committeeRoleBO.getDisplayName(),
                committeeRoleBO.getDescription(),
                committeeRoleBO.getVisible()
        );
    }

    @Override
    public CommitteeRoleBO convert(final CommitteeRole committeeRole) {
        CommitteeRoleBO committeeRoleBO = new CommitteeRoleBO();
        committeeRoleBO.setId(committeeRole.getCommitteeRoleId());
        committeeRoleBO.setName(committeeRole.getCommitteeRole().getValue());
        committeeRoleBO.setDisplayName(committeeRole.getDisplayName());
        committeeRoleBO.setDescription(committeeRole.getDescription());
        committeeRoleBO.setVisible(committeeRole.getVisible());
        return committeeRoleBO;
    }

    public CommitteeRoleList convert(final List<CommitteeRoleBO> committeeRoles) {
        return new CommitteeRoleList(committeeRoles.stream()
                .map(this::convert)
                .collect(Collectors.toList()));
    }

}
