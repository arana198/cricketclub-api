package com.cricketclub.service.user

import com.cricketclub.domain.user.UserBO
import com.cricketclub.domain.user.UserPasswordTokenBO
import com.cricketclub.repository.user.UserPasswordTokenRepository
import spock.lang.Specification

class UserPasswordTokenServiceImplTest extends Specification {

    private static final Long USER_ID = 4
    private static final Long ID = 2
    private static final String TOKEN = "token"

    private UserPasswordTokenRepository userPasswordTokenRepository
    private UserPasswordTokenBO userPasswordTokenBO
    private UserBO userBO

    private UserPasswordTokenServiceImpl underTest

    def setup() {
        userPasswordTokenRepository = Mock(UserPasswordTokenRepository)
        userPasswordTokenBO = Mock(UserPasswordTokenBO)
        userBO = Mock(UserBO)

        underTest = new UserPasswordTokenServiceImpl(userPasswordTokenRepository:userPasswordTokenRepository)

        userBO.getId() >> ID
        userPasswordTokenBO.getUser() >> userBO
    }

    def "test save"() {
        when:
            underTest.save(userPasswordTokenBO)
        then:
            1 * userPasswordTokenRepository.save(userPasswordTokenBO)
    }

    def "test findByUserId"() {
        when:
            UserPasswordTokenBO result = underTest.findByUserId(USER_ID)
        then:
            1 * userPasswordTokenRepository.findByUserId(USER_ID) >> Optional.of(userPasswordTokenBO)
            result == userPasswordTokenBO
    }

    def "test findByUserId when no token found"() {
        when:
            UserPasswordTokenBO result = underTest.findByUserId(USER_ID)
        then:
            1 * userPasswordTokenRepository.findByUserId(USER_ID) >> Optional.empty()
            result != null
    }

    def "test findByUserIdAndToken"() {
        when:
            Optional<UserPasswordTokenBO> result = underTest.findByUserIdAndToken(USER_ID, TOKEN)
        then:
            1 * userPasswordTokenRepository.findByUserIdAndToken(USER_ID, TOKEN) >> Optional.of(userPasswordTokenBO)
            result.isPresent()
            result.get() == userPasswordTokenBO
    }

    def "test delete"() {
        when:
            underTest.delete(userPasswordTokenBO)
        then:
            1 * userPasswordTokenRepository.delete(userPasswordTokenBO)
    }
}