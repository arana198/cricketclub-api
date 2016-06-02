package com.cricketclub.service.service

import com.cricketclub.user.domain.RoleBO
import com.cricketclub.user.repository.RoleRepository
import com.cricketclub.user.service.RoleServiceImpl
import spock.lang.Specification

class RoleServiceImplTest extends Specification {

    private static final Integer ID = 2
    private static final RoleBO.Role ROLE = RoleBO.Role.ROLE_ADMIN

    private RoleRepository roleRepository
    private RoleBO roleBO

    private RoleServiceImpl underTest

    def setup() {
        roleRepository = Mock(RoleRepository)
        roleBO = Mock(RoleBO)

        underTest = new RoleServiceImpl(roleRepository:roleRepository);
    }

    def "test findActiveRoles"() {
        given:
            List<RoleBO> roles = Arrays.asList(roleBO)
        when:
            List<RoleBO> result = underTest.findActiveRoles()
        then:
            1 * roleRepository.findBySelectable(true) >> roles
            result.size() == 1
            result.get(0)== roleBO
    }

    def "test findById"() {
        when:
            Optional<RoleBO> result = underTest.findById(ID)
        then:
            1 * roleRepository.findById(ID) >> Optional.of(roleBO)
            result.isPresent()
            result.get() == roleBO
    }

    def "test findByName"() {
        when:
            Optional<RoleBO> result = underTest.findByName(ROLE)
        then:
            1 * roleRepository.findByName(ROLE) >> Optional.of(roleBO)
            result.isPresent()
            result.get() == roleBO
    }

    def "test save"() {
        when:
            underTest.save(roleBO)
        then:
        1 * roleRepository.save(roleBO)
    }
}