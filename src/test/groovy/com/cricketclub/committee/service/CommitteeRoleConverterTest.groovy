package com.cricketclub.committee.service

import com.cricketclub.committee.service.CommitteeRoleConverter
import com.cricketclub.committee.domain.CommitteeRoleBO
import com.cricketclub.committee.dto.CommitteeRole
import com.cricketclub.committee.dto.CommitteeRoleList
import spock.lang.Specification

class CommitteeRoleConverterTest extends Specification {

    private static final String DESCRIPTION = "Description"
    private static final CommitteeRoleBO.CommitteeRole ROLE = CommitteeRoleBO.CommitteeRole.CHAIRMAN
    private static final String DISPLAY_NAME = ROLE.name()
    private static final Integer COMMITTEE_ROLE_ID = 912

    private CommitteeRoleBO committeeRoleBO
    private CommitteeRole committeeRole

    private CommitteeRoleConverter underTest

    def setup() {
        committeeRoleBO = Mock(CommitteeRoleBO)
        committeeRole = Mock(CommitteeRole)

        underTest = new CommitteeRoleConverter()

        committeeRoleBO.getId() >> COMMITTEE_ROLE_ID
        committeeRoleBO.getDescription() >> DESCRIPTION
        committeeRoleBO.getName() >> ROLE
        committeeRoleBO.getDisplayName() >> DISPLAY_NAME
    }

    def "test transform to committeeRole success"() {
        when:
            CommitteeRole result = underTest.convert(committeeRoleBO)
        then:
            result != null
            result.getCommitteeRoleId() == COMMITTEE_ROLE_ID
            result.getDescription() == DESCRIPTION
            result.getDisplayName() == DISPLAY_NAME
            result.getCommitteeRole() == ROLE
    }

    def "test transform to committeeRoleList success"() {
        given:
            List<CommitteeRoleBO> committeeRoleBOList = Arrays.asList(committeeRoleBO)
        when:
            List<CommitteeRole> result = underTest.convert(committeeRoleBOList)
        then:
            result.size() == 1
    }

    def "test transformToList success"() {
        given:
            List<CommitteeRole> committeeRolesList = Arrays.asList(committeeRole)
        when:
            CommitteeRoleList result = underTest.convert(committeeRolesList)
        then:
            result != null
            result.getCommitteeRoles().size() == 1
    }
}