package com.cricketclub.committee.service

import com.cricketclub.committee.domain.CommitteeRoleBO
import com.cricketclub.committee.dto.CommitteeRole
import com.cricketclub.committee.dto.CommitteeRoleList
import com.cricketclub.committee.repository.CommitteeRoleRepository
import spock.lang.Specification

class CommitteeRoleServiceImplTest extends Specification {
    private CommitteeRoleRepository committeeRoleRepository  = Mock(CommitteeRoleRepository)
    private CommitteeRoleConverter committeeRoleConverter = Mock(CommitteeRoleConverter)

    private CommitteeRoleBO committeeRoleBO
    private CommitteeRole committeeRole
    private CommitteeRoleList committeeRoleList

    private CommitteeRoleServiceImpl underTest

    def setup() {
        committeeRoleBO = Mock(CommitteeRoleBO)
        committeeRole = Mock(CommitteeRole)
        committeeRoleList = Mock(CommitteeRoleList)

        underTest = new CommitteeRoleServiceImpl(committeeRoleRepository, committeeRoleConverter)
    }

    def "should get active committee roles"() {
        given:
            List<CommitteeRoleBO> committeeRoleBOList = new ArrayList<>()
            List<CommitteeRole> committeeRoles = new ArrayList<>()
            committeeRoleBOList.add(committeeRoleBO)
            committeeRoles.add(committeeRole)
        when:
            Optional<CommitteeRoleList> result = underTest.getActiveCommitteRole()
        then:
            1 * committeeRoleRepository.findByVisible(true) >> committeeRoleBOList
            1 * committeeRoleConverter.convert(committeeRoleBOList) >> committeeRoleList
            result.isPresent()
            result.get() == committeeRoleList
    }

    def "should return empty optional when no active committee roles found"() {
        when:
            Optional<CommitteeRoleList> result = underTest.getActiveCommitteRole()
        then:
            1 * committeeRoleRepository.findByVisible(true) >> new ArrayList<CommitteeRoleBO>()
            0 * committeeRoleConverter.convert(_)
            !result.isPresent()
    }

    def "should return optional of committee role"() {
        given:
            Integer id = 1
        when:
            Optional<CommitteeRoleBO> result = underTest.findById(id)
        then:
            1 * committeeRoleRepository.findById(id) >> Optional.of(committeeRoleBO)
            result.isPresent()
            result.get() == committeeRoleBO
    }
}