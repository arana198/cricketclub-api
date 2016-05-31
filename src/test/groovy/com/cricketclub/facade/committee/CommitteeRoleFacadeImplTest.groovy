package com.cricketclub.facade.committee

import com.cricketclub.api.resource.committee.CommitteeRole
import com.cricketclub.api.resource.committee.CommitteeRoleList
import com.cricketclub.domain.committee.CommitteeRoleBO

import com.cricketclub.service.committee.CommitteeRoleService
import spock.lang.Specification

class CommitteeRoleFacadeImplTest extends Specification {

    private CommitteeRoleService committeeRoleService
    private CommitteeRoleMapper committeeRoleMapper

    private CommitteeRoleBO committeeRoleBO
    private CommitteeRole committeeRole
    private CommitteeRoleList committeeRoleList

    private CommitteeRoleFacadeImpl underTest

    def setup() {
        committeeRoleService = Mock(CommitteeRoleService)
        committeeRoleMapper = Mock(CommitteeRoleMapper)

        committeeRoleBO = Mock(CommitteeRoleBO)
        committeeRole = Mock(CommitteeRole)
        committeeRoleList = Mock(CommitteeRoleList)

        underTest = new CommitteeRoleFacadeImpl(
                committeeRoleService:committeeRoleService,
                mapper:committeeRoleMapper
        )
    }

    def "test getActiveCommitteRole success"() {
        given:
            List<CommitteeRoleBO> committeeRoleBOList = new ArrayList<>()
            List<CommitteeRole> committeeRoles = new ArrayList<>()
            committeeRoleBOList.add(committeeRoleBO)
            committeeRoles.add(committeeRole)
        when:
            Optional<CommitteeRoleList> result = underTest.getActiveCommitteRole()
        then:
            1 * committeeRoleService.getActiveCommitteeRoles() >> committeeRoleBOList
            1 * committeeRoleMapper.transform(committeeRoleBOList) >> committeeRoles
            1 * committeeRoleMapper.transformToList(committeeRoles) >> committeeRoleList
            result.isPresent()
            result.get() == committeeRoleList
    }

    def "test getActiveCommitteRole when committee role is empty"() {
        when:
            Optional<CommitteeRoleList> result = underTest.getActiveCommitteRole()
        then:
            1 * committeeRoleService.getActiveCommitteeRoles() >> committeeRoleBOs
            0 * committeeRoleMapper.transform(_)
            0 * committeeRoleMapper.transformToList(_)
            !result.isPresent()
        where:
            committeeRoleBOs << [null, new ArrayList<CommitteeRoleBO>()]

    }
}