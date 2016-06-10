package com.cricketclub.committee.role.service;

import com.cricketclub.committee.role.dto.CommitteeRole;
import com.cricketclub.committee.role.dto.CommitteeRoleList;
import com.cricketclub.committee.role.domain.CommitteeRoleBO;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class CommitteeRoleConverter implements Converter<CommitteeRoleBO, CommitteeRole> {

    @Override
    public CommitteeRole convert(final CommitteeRoleBO committeeRoleBO) {
        return new CommitteeRole(
                committeeRoleBO.getId(),
                committeeRoleBO.getName(),
                committeeRoleBO.getDisplayName(),
                committeeRoleBO.getDescription(),
                committeeRoleBO.getVisible()
        );
    }

    public CommitteeRoleBO convert(final CommitteeRole committeeRole) {
        CommitteeRoleBO committeeRoleBO = new CommitteeRoleBO();
        committeeRoleBO.setName(committeeRole.getCommitteeRole());
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
