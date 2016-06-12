package com.cricketclub.user.service

import com.cricketclub.user.dto.Role
import com.cricketclub.user.dto.RoleList
import com.cricketclub.user.domain.RoleBO
import spock.lang.Specification

class RoleConverterTest extends Specification {

    private static final String DESCRIPTION = "Description"
    private static final RoleBO.Role ROLE = RoleBO.Role.ROLE_ADMIN
    private static final Integer ROLE_ID = 912

    private RoleBO roleBO
    private Role role

    private RoleConverter underTest

    def setup() {
        roleBO = Mock(RoleBO)
        role = Mock(Role)

        underTest = new RoleConverter()

        roleBO.getId() >> ROLE_ID
        roleBO.getDescription() >> DESCRIPTION
        roleBO.getName() >> ROLE
    }

    def "test transform to role success"() {
        when:
            Role result = underTest.convert(roleBO)
        then:
            result != null
            result.getRoleId() == ROLE_ID
            result.getDescription() == DESCRIPTION
            result.getName() == ROLE
    }

    def "test transform to roleList success"() {
        given:
            List<RoleBO> roleBOList = Arrays.asList(roleBO)
        when:
            List<Role> result = underTest.convert(roleBOList)
        then:
            result.size() == 1
    }

    def "test transformToList success"() {
        given:
            List<Role> roleList = Arrays.asList(role)
        when:
            RoleList result = underTest.convert(roleList)
        then:
            result != null
            result.getRoles().size() == 1
    }
}