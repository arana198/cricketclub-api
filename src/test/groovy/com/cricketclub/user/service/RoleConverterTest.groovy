package com.cricketclub.user.service

import com.cricketclub.user.dto.Role
import com.cricketclub.user.dto.RoleList
import com.cricketclub.user.domain.RoleBO
import org.mockito.internal.util.collections.Sets
import spock.lang.Specification

class RoleConverterTest extends Specification {

    private static final String DESCRIPTION = "Description"
    private static final Role.UserRole ROLE = Role.UserRole.ROLE_ADMIN
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

    def "should return Role"() {
        when:
            Role result = underTest.convert(roleBO)
        then:
            result != null
            result.getRoleId() == ROLE_ID
            result.getDescription() == DESCRIPTION
            result.getName() == ROLE
    }

    def "should return RoleList"() {
        given:
            Set<RoleBO> roleBOList = Sets.newSet(roleBO)
        when:
            RoleList result = underTest.convert(roleBOList)
        then:
            result != null
            result.getRoles().size() == 1
    }
}