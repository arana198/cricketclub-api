package com.cricketclub.user.repository

import com.cricketclub.config.BaseRepositoryTest
import com.cricketclub.user.domain.UserPasswordTokenBO
import com.cricketclub.user.domain.UserStatusBO
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.operation.Operation
import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom
import static com.ninja_squad.dbsetup.Operations.insertInto
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf

class UserPasswordTokenRepositoryTest extends BaseRepositoryTest {

    private static final Operation DELETE_ALL =  deleteAllFrom("USER_PASSWORD_TOKEN")
    private static final Operation INSERT_REFERENCE_DATA =
            sequenceOf(DELETE_ALL,
                    insertInto("USER_PASSWORD_TOKEN")
                            .columns("id", "user_id", "token", "created_ts")
                            .values(1, 1, "token1", new Date())
                            .values(2, 2, "token2", new Date())
                            .build())

    @Shared
    private static DbSetup DBSETUP

    @Autowired
    private UserPasswordTokenRepository underTest

    def setup() {
        if(DBSETUP == null) {
            DBSETUP = new DbSetup(new DataSourceDestination(dataSource), INSERT_REFERENCE_DATA)
            DB_SETUP_TRACKER.launchIfNecessary(DBSETUP)
        }
    }

    def "should return user password token for a given user and token"() {
        given:
            String token = "token1"
            Long userId = 1
        when:
            Optional<UserPasswordTokenBO> result = underTest.findByUserIdAndToken(userId, token)
        then:
            result.isPresent()
            UserPasswordTokenBO userPasswordTokenBO = result.get()
            userPasswordTokenBO.token == token
            userPasswordTokenBO.user.id == userId
    }

    def "should return user password token for a given user id"() {
        given:
            UserStatusBO.UserStatus name = UserStatusBO.UserStatus.PENDING
        when:
            Optional<UserPasswordTokenBO> result = underTest.findByUserId(2)
        then:
            result.isPresent()
            UserPasswordTokenBO userPasswordTokenBO = result.get()
            userPasswordTokenBO.id == 2
            userPasswordTokenBO.user.id == 2
    }
}
