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
    }

    def "should convert committeeRoleBO to committeeRole"() {
        given:
            committeeRoleBO.getId() >> COMMITTEE_ROLE_ID
            committeeRoleBO.getDescription() >> DESCRIPTION
            committeeRoleBO.getName() >> ROLE
            committeeRoleBO.getDisplayName() >> DISPLAY_NAME
        when:
            CommitteeRole result = underTest.convert(committeeRoleBO)
        then:
            result != null
            result.getCommitteeRoleId() == COMMITTEE_ROLE_ID
            result.getDescription() == DESCRIPTION
            result.getDisplayName() == DISPLAY_NAME
            result.getCommitteeRole() == ROLE
    }

    def "should convert CommitteeRole to CommitteeRoleBO"() {
        given:
            committeeRole.getCommitteeRoleId() >> COMMITTEE_ROLE_ID
            committeeRole.getDescription() >> DESCRIPTION
            committeeRole.getCommitteeRole() >> ROLE
            committeeRole.getDisplayName() >> DISPLAY_NAME
        when:
            CommitteeRoleBO result = underTest.convert(committeeRole)
        then:
            result != null
            result.getId() == COMMITTEE_ROLE_ID
            result.getDescription() == DESCRIPTION
            result.getDisplayName() == DISPLAY_NAME
            result.getName() == ROLE
    }

    def "should transform list of committeeRole to CommitteeRoleList"() {
        given:
            List<CommitteeRoleBO> committeeRolesList = Arrays.asList(committeeRoleBO)
            committeeRoleBO.getId() >> COMMITTEE_ROLE_ID
            committeeRoleBO.getDescription() >> DESCRIPTION
            committeeRoleBO.getName() >> ROLE
            committeeRoleBO.getDisplayName() >> DISPLAY_NAME
        when:
            CommitteeRoleList result = underTest.convert(committeeRolesList)
        then:
            result != null
            result.getCommitteeRoles().size() == 1
    }
}