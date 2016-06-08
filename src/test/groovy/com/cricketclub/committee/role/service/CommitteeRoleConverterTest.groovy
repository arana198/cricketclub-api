package com.cricketclub.committee.role.service

import com.cricketclub.committee.role.domain.CommitteeRoleBO
import com.cricketclub.committee.role.dto.CommitteeRole
import com.cricketclub.committee.role.dto.CommitteeRoleList
import org.mapstruct.factory.Mappers
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

        underTest = Mappers.getMapper( CommitteeRoleConverter.class )

        committeeRoleBO.getId() >> COMMITTEE_ROLE_ID
        committeeRoleBO.getDescription() >> DESCRIPTION
        committeeRoleBO.getName() >> ROLE
        committeeRoleBO.getDisplayName() >> DISPLAY_NAME
    }

    def "test transform to committeeRole success"() {
        when:
            CommitteeRole result = underTest.transform(committeeRoleBO)
        then:
            result != null
            result.getCommitteeRoleId() == COMMITTEE_ROLE_ID
            result.getDescription() == DESCRIPTION
            result.getDisplayName() == DISPLAY_NAME
            result.getCommitteeRole() == ROLE
    }

    def "test transform to committeeRole when null input"() {
        when:
            CommitteeRole result = underTest.transform(null)
        then:
            result == null
    }

    def "test transform to committeeRoleList success"() {
        given:
            List<CommitteeRoleBO> committeeRoleBOList = Arrays.asList(committeeRoleBO)
        when:
            List<CommitteeRole> result = underTest.transform(committeeRoleBOList)
        then:
            result.size() == 1
    }

    def "test transform to committeeRoleList when null input"() {
        when:
            List<CommitteeRole> result = underTest.transform(null)
        then:
            result == null
    }

    def "test transformToList success"() {
        given:
            List<CommitteeRole> committeeRolesList = Arrays.asList(committeeRole)
        when:
            CommitteeRoleList result = underTest.transformToList(committeeRolesList)
        then:
            result != null
            result.getCommitteeRoles().size() == 1
    }
}