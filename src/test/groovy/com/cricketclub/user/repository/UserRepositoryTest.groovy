package com.cricketclub.user.repository

import com.cricketclub.config.BaseRepositoryTest
import com.cricketclub.user.domain.UserBO
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.operation.Operation
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom
import static com.ninja_squad.dbsetup.Operations.insertInto
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf

class UserRepositoryTest extends BaseRepositoryTest {
    private static final Operation INSERT_REFERENCE_DATA =
            sequenceOf(insertInto("USER")
                            .columns("username", "password", "user_status_id")
                            .values("usertest", "password", 1)
                            .values("usertest2", "password", 1)
                            .build())

    @Shared
    private static DbSetup DBSETUP

    @Autowired
    private UserRepository underTest

    def setup() {
        if(DBSETUP == null) {
            DBSETUP = new DbSetup(new DataSourceDestination(dataSource), INSERT_REFERENCE_DATA)
            DB_SETUP_TRACKER.launchIfNecessary(DBSETUP)
        }
    }

    def "should return user for a given id"() {
        given:
            Long id = 1
        when:
            Optional<UserBO> result = underTest.findById(id)
        then:
            result.isPresent()
            UserBO userBO = result.get()
            userBO.id == id
    }

    def "should return user with username for a given username"() {
        given:
            String username = "usertest2"
        when:
            Optional<UserBO> result = underTest.findByUsername(username)
        then:
            result.isPresent()
            UserBO userBO = result.get()
            userBO.username == username
    }

    def "should return user with username for a given username and password"() {
        given:
            String username = "usertest2"
        when:
            Optional<UserBO> result = underTest.findByUsernameAndPassword(username, "password")
        then:
            result.isPresent()
            UserBO userBO = result.get()
            userBO.username == username
    }
}
