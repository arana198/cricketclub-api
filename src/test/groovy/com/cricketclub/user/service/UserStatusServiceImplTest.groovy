package com.cricketclub.user.service

import com.cricketclub.user.domain.UserStatusBO
import com.cricketclub.user.repository.UserStatusRepository
import spock.lang.Specification

class UserStatusServiceImplTest extends Specification {

    private static final Integer ID = 2
    private static final UserStatusBO.UserStatus USER_STATUS = UserStatusBO.UserStatus.ACTIVE

    private UserStatusRepository userStatusRepository = Mock(UserStatusRepository)
    private UserStatusBO userStatusBO

    private UserStatusServiceImpl underTest

    def setup() {
        userStatusBO = Mock(UserStatusBO)

        underTest = new UserStatusServiceImpl(userStatusRepository);
    }

    def "should return Optional UserStatusBO for a given Id"() {
        when:
            Optional<UserStatusBO> result = underTest.findById(ID)
        then:
            1 * userStatusRepository.getOne(ID) >> userStatusBO
            result.isPresent()
            result.get() == userStatusBO
    }

    def "should return Optional UserStatusBO for a given UserStatus name"() {
        when:
            Optional<UserStatusBO> result = underTest.findByName(USER_STATUS)
        then:
            1 * userStatusRepository.findByName(USER_STATUS) >> Optional.of(userStatusBO)
            result.isPresent()
            result.get() == userStatusBO
    }
}