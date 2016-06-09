package com.cricketclub.committee

import com.cricketclub.committee.role.domain.CommitteeRoleBO
import com.cricketclub.committee.role.repository.CommitteeRoleRepository
import com.cricketclub.committee.role.CommitteeRoleServiceImpl
import spock.lang.Specification

class CommitteeRoleServiceImplTest extends Specification {

    private static final Integer ID = 1
    private static final CommitteeRoleBO.CommitteeRole COMMITTEE_ROLE = CommitteeRoleBO.CommitteeRole.CHAIRMAN

    private CommitteeRoleRepository committeeRoleRepository
    private CommitteeRoleBO committeeRoleBO

    private CommitteeRoleServiceImpl underTest

    def setup() {
        committeeRoleRepository = Mock(CommitteeRoleRepository)
        committeeRoleBO = Mock(CommitteeRoleBO)

        underTest = new CommitteeRoleServiceImpl(committeeRoleRepository:committeeRoleRepository);
    }

    def "test findById"() {
        when:
            Optional<CommitteeRoleBO> result = underTest.findById(ID)
        then:
            1 * committeeRoleRepository.findById(ID) >> Optional.of(committeeRoleBO)
            result.isPresent()
            result.get() == committeeRoleBO
    }

    def "test findByName"() {
        when:
            Optional<CommitteeRoleBO> result = underTest.findByName(COMMITTEE_ROLE)
        then:
            1 * committeeRoleRepository.findByName(COMMITTEE_ROLE) >> Optional.of(committeeRoleBO)
            result.isPresent()
            result.get() == committeeRoleBO
    }

    def "test getActiveCommitteeRoles"() {
        given:
            List<CommitteeRoleBO> roleBOList = new ArrayList<>()
            roleBOList.add(committeeRoleBO)
        when:
            List<CommitteeRoleBO> result = underTest.getActiveCommitteeRoles()
        then:
            1 * committeeRoleRepository.findByVisible(true) >> roleBOList
            roleBOList.size() == 1
            roleBOList.get(0) == committeeRoleBO
    }
}