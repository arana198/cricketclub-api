package com.cricketclub.service.user

import com.cricketclub.user.dto.Role
import com.cricketclub.user.dto.RoleList
import com.cricketclub.user.domain.RoleBO
import com.cricketclub.user.exception.NoSuchRoleException

import spock.lang.Specification

class RoleServiceImplTest extends Specification {

    private static final Integer ROLE_ID = 912
    private static final Boolean SELECTABLE = false

    private RoleBO roleBO
    private Role role
    private RoleList roleList

    private RoleServiceInterface roleService
    private RoleMapper roleMapper

    private RoleServiceImpl underTest

    def setup() {
        roleBO = Mock(RoleBO)
        role = Mock(Role)
        roleList = Mock(RoleList)

        roleMapper = Mock(RoleMapper)
        roleService = Mock(RoleServiceInterface)

        underTest = new RoleServiceImpl(mapper: roleMapper, roleRepository: roleService)
    }

    def "test findActiveRoles success"() {
        given:
            List<RoleBO> roleBOList = Arrays.asList(roleBO)
            List<Role> roles = Arrays.asList(role)
        when:
            Optional<RoleList> result = underTest.findActiveRoles()
        then:
            1 * roleService.findActiveRoles() >> roleBOList
            1 * roleMapper.transform(roleBOList) >> roles
            1 * roleMapper.transformToList(roles) >> roleList
            result.isPresent()
            result.get() != null
    }

    def "test findActiveRoles when no roles found"() {
        when:
            Optional<RoleList> result = underTest.findActiveRoles()
        then:
            1 * roleService.findActiveRoles() >> new ArrayList<>()
            0 * roleMapper.transform(_)
            0 * roleMapper.transformToList(_)
            !result.isPresent()
    }

    def "test updateRole success"() {
        when:
            underTest.updateRole(ROLE_ID, SELECTABLE)
        then:
            1 * roleService.findById(ROLE_ID) >> Optional.of(roleBO)
            1 * roleBO.setSelectable(SELECTABLE)
            1 * roleService.save(roleBO)
    }

    def "test updateRole when role not found"() {
        when:
            underTest.updateRole(ROLE_ID, SELECTABLE)
        then:
            1 * roleService.findById(ROLE_ID) >> Optional.empty()
            0 * roleBO.setSelectable(SELECTABLE)
            0 * roleService.save(roleBO)
            def ex = thrown(NoSuchRoleException)
    }
}