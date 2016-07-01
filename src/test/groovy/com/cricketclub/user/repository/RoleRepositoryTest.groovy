package com.cricketclub.user.repository

import com.cricketclub.config.BaseRepositoryTest
import com.cricketclub.user.domain.RoleBO
import com.cricketclub.user.domain.UserBO
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.operation.Operation
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom
import static com.ninja_squad.dbsetup.Operations.insertInto
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf

class RoleRepositoryTest extends BaseRepositoryTest {

    private static final Operation DELETE_ALL =  deleteAllFrom("USER_ROLES", "ROLE")
    private static final Operation INSERT_REFERENCE_DATA =
            sequenceOf(DELETE_ALL,
                    insertInto("ROLE")
                            .columns("id", "name", "description", "presedence_order", "is_selectable")
                            .values(1, "ROLE_ADMIN", "description for role1", 1, true)
                            .values(2, "ROLE_CLUB_ADMIN", "description for role2", 2, true)
                            .values(3, "ROLE_USER", "description for role3", 3, false)
                            .values(4, "ROLE_CAPTAIN", "description for role4", 4, false)
                            .values(5, "ROLE_PLAYER", "description for role5", 5, true)
                            .build())

    @Shared
    private static DbSetup DBSETUP

    @Autowired
    private RoleRepository underTest

    def setup() {
        if(DBSETUP == null) {
            DBSETUP = new DbSetup(new DataSourceDestination(dataSource), INSERT_REFERENCE_DATA)
            DB_SETUP_TRACKER.launchIfNecessary(DBSETUP)
        }
    }

    def "should return role with id 1"() {
        when:
            Optional<RoleBO> result = underTest.findById(1)
        then:
            result.isPresent()
            RoleBO roleBO = result.get()
            roleBO.id == 1
    }

    def "should return role with name role4 when finding by role name"() {
        given:
            RoleBO.Role roleName = RoleBO.Role.ROLE_ADMIN
        when:
            Optional<RoleBO> result = underTest.findByName(roleName)
        then:
            result.isPresent()
            RoleBO roleBO = result.get()
            roleBO.id == 1
            roleBO.name == roleName
    }

    def "should return hidden role when finding by not selectable"() {
        when:
            List<RoleBO> result = underTest.findBySelectable(false)
        then:
            result.size() == 2
            RoleBO roleBO = result.get(0)
            roleBO.selectable == false
    }
}
