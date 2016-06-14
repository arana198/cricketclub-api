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

    private RoleRepository roleRepository = Mock(RoleRepository)
    private RoleConverter roleConverter = Mock(RoleConverter)

    private RoleServiceImpl underTest

    def setup() {
        roleBO = Mock(RoleBO)
        role = Mock(Role)
        roleList = Mock(RoleList)

        underTest = new RoleServiceImpl(roleRepository, roleConverter)
    }

    def "should return active roles"() {
        given:
            List<RoleBO> roleBOList = Arrays.asList(roleBO)
        when:
            Optional<RoleList> result = underTest.findActiveRoles()
        then:
            1 * roleRepository.findBySelectable(true) >> roleBOList
            1 * roleConverter.convert(roleBO) >> role
            result.isPresent()
            RoleList roleListResult = result.get()
            roleListResult != null
            roleListResult.getRoles().size() == 1
            roleListResult.getRoles().get(0)== role
    }

    def "should return empty optional when no roles found"() {
        when:
            Optional<RoleList> result = underTest.findActiveRoles()
        then:
            1 * roleRepository.findBySelectable(true) >> new ArrayList<>()
            0 * roleConverter.convert(_)
            result.isPresent()
            result.get().getRoles().size() == 0
    }

    def "should update role when given roleId and selectable flag"() {
        when:
            underTest.updateRole(ROLE_ID, SELECTABLE)
        then:
            1 * roleRepository.findById(ROLE_ID) >> Optional.of(roleBO)
            1 * roleBO.setSelectable(SELECTABLE)
            1 * roleRepository.save(roleBO)
    }

    def "should throw NoSuchRoleException when updateRole does not find any role"() {
        when:
            underTest.updateRole(ROLE_ID, SELECTABLE)
        then:
            1 * roleRepository.findById(ROLE_ID) >> Optional.empty()
            0 * roleBO.setSelectable(SELECTABLE)
            0 * roleRepository.save(roleBO)
            def ex = thrown(NoSuchRoleException)
    }
}