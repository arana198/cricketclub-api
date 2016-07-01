package com.cricketclub.user.repository

import com.cricketclub.config.BaseRepositoryTest
import com.cricketclub.user.domain.RoleBO
import com.cricketclub.user.domain.UserStatusBO
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.operation.Operation
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom
import static com.ninja_squad.dbsetup.Operations.insertInto
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf

class UserStatusRepositoryTest extends BaseRepositoryTest {

    private static final Operation DELETE_ALL =  deleteAllFrom("USER_ROLES", "USER", "USER_STATUS")
    private static final Operation INSERT_REFERENCE_DATA =
            sequenceOf(DELETE_ALL,
                    insertInto("USER_STATUS")
                            .columns("id", "name", "description", "is_selectable")
                            .values(1, "ACTIVE", "description for role1", true)
                            .values(2, "PENDING", "description for role2", true)
                            .values(3, "SUSPENDED", "description for role3", false)
                            .values(4, "BLACKLISTED", "description for role4", false)
                            .build())

    @Shared
    private static DbSetup DBSETUP

    @Autowired
    private UserStatusRepository underTest

    def setup() {
        if(DBSETUP == null) {
            DBSETUP = new DbSetup(new DataSourceDestination(dataSource), INSERT_REFERENCE_DATA)
            DB_SETUP_TRACKER.launchIfNecessary(DBSETUP)
        }
    }

    def "should return user status with id 1"() {
        when:
            Optional<UserStatusBO> result = underTest.findById(1)
        then:
            result.isPresent()
            UserStatusBO userStatusBO = result.get()
            userStatusBO.id == 1
    }

    def "should return user status with name PENDING when finding by role name"() {
        given:
            UserStatusBO.UserStatus name = UserStatusBO.UserStatus.PENDING
        when:
            Optional<UserStatusBO> result = underTest.findByName(name)
        then:
            result.isPresent()
            UserStatusBO userStatusBO = result.get()
            userStatusBO.id == 2
            userStatusBO.name == name
    }
}
