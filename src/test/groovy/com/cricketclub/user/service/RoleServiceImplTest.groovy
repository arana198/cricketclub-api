package com.cricketclub.user.service

import com.cricketclub.user.dto.Role
import com.cricketclub.user.dto.RoleList
import com.cricketclub.user.domain.RoleBO
import com.cricketclub.user.exception.NoSuchRoleException
import com.cricketclub.user.repository.RoleRepository
import spock.lang.Specification

class RoleServiceImplTest extends Specification {

    private static final Integer ROLE_ID = 912
    private static final Boolean SELECTABLE = false

    private RoleBO roleBO
    private Role role
    private RoleList roleList

    private RoleRepository roleRepository
    private RoleConverter roleConverter

    private RoleServiceImpl underTest

    def setup() {
        roleBO = Mock(RoleBO)
        role = Mock(Role)
        roleList = Mock(RoleList)

        roleConverter = Mock(RoleConverter)

        underTest = new RoleServiceImpl(roleConverter: roleConverter, roleRepository: roleRepository)
    }

    def "test findActiveRoles success"() {
        given:
            List<RoleBO> roleBOList = Arrays.asList(roleBO)
            List<Role> roles = Arrays.asList(role)
        when:
            Optional<RoleList> result = underTest.findActiveRoles()
        then:
            1 * roleRepository.findBySelectable(true) >> roleBOList
            1 * roleConverter.convert(roleBOList) >> roles
            1 * roleConverter.convert(roles) >> roleList
            result.isPresent()
            result.get() != null
    }

    def "test findActiveRoles when no roles found"() {
        when:
            Optional<RoleList> result = underTest.findActiveRoles()
        then:
            1 * roleRepository.findActiveRoles() >> new ArrayList<>()
            0 * roleConverter.convert(_)
            0 * roleConverter.convert(_)
            !result.isPresent()
    }

    def "test updateRole success"() {
        when:
            underTest.updateRole(ROLE_ID, SELECTABLE)
        then:
            1 * roleRepository.findById(ROLE_ID) >> Optional.of(roleBO)
            1 * roleBO.setSelectable(SELECTABLE)
            1 * roleRepository.save(roleBO)
    }

    def "test updateRole when role not found"() {
        when:
            underTest.updateRole(ROLE_ID, SELECTABLE)
        then:
            1 * roleRepository.findById(ROLE_ID) >> Optional.empty()
            0 * roleBO.setSelectable(SELECTABLE)
            0 * roleRepository.save(roleBO)
            def ex = thrown(NoSuchRoleException)
    }
}