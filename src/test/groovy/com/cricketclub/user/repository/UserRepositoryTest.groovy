package com.cricketclub.user.repository

import com.cricketclub.config.BaseRepositoryTest
import com.cricketclub.user.domain.UserBO
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.DbSetupTracker
import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.operation.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared

import javax.sql.DataSource

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom
import static com.ninja_squad.dbsetup.Operations.insertInto
import static com.ninja_squad.dbsetup.Operations.sql
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf

class UserRepositoryTest extends BaseRepositoryTest {

    private static final Operation DELETE_ALL =  deleteAllFrom("USER_ROLES", "USER")
    private static final Operation INSERT_REFERENCE_DATA =
            sequenceOf(insertInto("USER")
                            .columns("ID", "username", "password", "user_status_id")
                            .values(1, "test", "password", 1)
                            .values(2, "test2", "password", 1)
                            .build())

    @Shared
    private static DbSetup DBSETUP

    @Autowired
    private UserRepository userRepository

    def setup() {
        if(DBSETUP == null) {
            DBSETUP = new DbSetup(new DataSourceDestination(dataSource), INSERT_REFERENCE_DATA)
            DB_SETUP_TRACKER.launchIfNecessary(DBSETUP)
        }
    }

    def "should return user with id 1"() {
        when:
            Optional<UserBO> result = userRepository.findById(1)
        then:
            result.isPresent()
            UserBO userBO = result.get()
            userBO.id == 1
    }

    def "should return user with username test2"() {
        when:
            Optional<UserBO> result = userRepository.findByUsername("test2")
        then:
            result.isPresent()
            UserBO userBO = result.get()
            userBO.id == 2
            userBO.username == "test2"
    }
}
