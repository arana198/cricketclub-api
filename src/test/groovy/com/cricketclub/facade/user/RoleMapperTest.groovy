package com.cricketclub.facade.user

import com.cricketclub.api.resource.committee.CommitteeRole
import com.cricketclub.api.resource.committee.CommitteeRoleList
import com.cricketclub.api.resource.user.Role
import com.cricketclub.api.resource.user.RoleList
import com.cricketclub.domain.committee.CommitteeRoleBO
import com.cricketclub.domain.user.RoleBO
import com.cricketclub.facade.committee.CommitteeRoleMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class RoleMapperTest extends Specification {

    private static final String DESCRIPTION = "Description"
    private static final RoleBO.Role ROLE = RoleBO.Role.ROLE_ADMIN
    private static final Integer ROLE_ID = 912

    private RoleBO roleBO
    private Role role

    private RoleMapper underTest

    def setup() {
        roleBO = Mock(RoleBO)
        role = Mock(Role)

        underTest = Mappers.getMapper( RoleMapper.class )

        roleBO.getId() >> ROLE_ID
        roleBO.getDescription() >> DESCRIPTION
        roleBO.getName() >> ROLE
    }

    def "test transform to role success"() {
        when:
            Role result = underTest.transform(roleBO)
        then:
            result != null
            result.getRoleId() == ROLE_ID
            result.getDescription() == DESCRIPTION
            result.getName() == ROLE
    }

    def "test transform to role when null input"() {
        when:
            Role result = underTest.transform(null)
        then:
            result == null
    }

    def "test transform to roleList success"() {
        given:
            List<RoleBO> roleBOList = Arrays.asList(roleBO)
        when:
            List<Role> result = underTest.transform(roleBOList)
        then:
            result.size() == 1
    }

    def "test transform to roleList when null input"() {
        when:
            List<Role> result = underTest.transform(null)
        then:
            result == null
    }

    def "test transformToList success"() {
        given:
            List<Role> roleList = Arrays.asList(role)
        when:
            RoleList result = underTest.transformToList(roleList)
        then:
            result != null
            result.getRoles().size() == 1
    }
}