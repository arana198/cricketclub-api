package com.cricketclub.facade.committee

import com.cricketclub.api.resource.committee.CommitteeMember
import com.cricketclub.api.resource.committee.CommitteeMemberList
import com.cricketclub.api.resource.committee.CommitteeRole
import com.cricketclub.api.resource.committee.CommitteeRoleList
import com.cricketclub.api.resource.user.User
import com.cricketclub.domain.committee.CommitteeMemberBO
import com.cricketclub.domain.committee.CommitteeRoleBO
import com.cricketclub.domain.user.RoleBO
import com.cricketclub.domain.user.UserBO
import com.cricketclub.facade.user.UserMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class CommitteeRoleMapperTest extends Specification {

    private static final String DESCRIPTION = "Description"
    private static final CommitteeRoleBO.CommitteeRole ROLE = CommitteeRoleBO.CommitteeRole.CHAIRMAN
    private static final String DISPLAY_NAME = ROLE.name()
    private static final Integer COMMITTEE_ROLE_ID = 912

    private CommitteeRoleBO committeeRoleBO
    private CommitteeRole committeeRole

    private CommitteeRoleMapper underTest

    def setup() {
        committeeRoleBO = Mock(CommitteeRoleBO)
        committeeRole = Mock(CommitteeRole)

        underTest = Mappers.getMapper( CommitteeRoleMapper.class )

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