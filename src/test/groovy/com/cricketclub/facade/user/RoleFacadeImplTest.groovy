package com.cricketclub.facade.user

import com.cricketclub.api.resource.user.Role
import com.cricketclub.api.resource.user.RoleList
import com.cricketclub.domain.user.RoleBO
import com.cricketclub.exception.user.NoSuchRoleException
import com.cricketclub.service.user.RoleService
import spock.lang.Specification

class RoleFacadeImplTest extends Specification {

    private static final Integer ROLE_ID = 912
    private static final Boolean SELECTABLE = false

    private RoleBO roleBO
    private Role role
    private RoleList roleList

    private RoleService roleService
    private RoleMapper roleMapper

    private RoleFacadeImpl underTest

    def setup() {
        roleBO = Mock(RoleBO)
        role = Mock(Role)
        roleList = Mock(RoleList)

        roleMapper = Mock(RoleMapper)
        roleService = Mock(RoleService)

        underTest = new RoleFacadeImpl(mapper: roleMapper, roleService: roleService)
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