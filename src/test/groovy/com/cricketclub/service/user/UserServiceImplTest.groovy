package com.cricketclub.service.user

import com.cricketclub.domain.user.UserBO
import com.cricketclub.domain.user.UserPasswordTokenBO
import com.cricketclub.repository.user.UserRepository
import spock.lang.Specification

class UserServiceImplTest extends Specification {

    private static final Integer ID = 2
    private static final String USERNAME = "username"

    private UserRepository userRepository
    private UserBO userBO

    private UserServiceImpl underTest

    def setup() {
        userRepository = Mock(UserRepository)
        userBO = Mock(UserBO)

        underTest = new UserServiceImpl(userRepository:userRepository);
    }

    def "test save"() {
        when:
            underTest.save(userBO)
        then:
            userBO.getUsername() >> USERNAME
            1 * userRepository.save(userBO)
    }

    def "test findById"() {
        when:
            Optional<UserBO> result = underTest.findById(ID)
        then:
            1 * userRepository.findById(ID) >> Optional.of(userBO)
            result.isPresent()
            result.get() == userBO
    }

    def "test findByName"() {
        when:
            Optional<UserBO> result = underTest.findByUsername(USERNAME)
        then:
            1 * userRepository.findByUsername(USERNAME) >> Optional.of(userBO)
            result.isPresent()
            result.get() == userBO
    }
}