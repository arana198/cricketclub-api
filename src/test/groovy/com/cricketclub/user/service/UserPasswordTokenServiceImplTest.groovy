package com.cricketclub.user.service

import com.cricketclub.user.domain.UserBO
import com.cricketclub.user.domain.UserPasswordTokenBO
import com.cricketclub.user.repository.UserPasswordTokenRepository
import spock.lang.Specification

class UserPasswordTokenServiceImplTest extends Specification {

    private static final Long USER_ID = 4
    private static final Long ID = 2
    private static final String TOKEN = "token"

    private UserPasswordTokenRepository userPasswordTokenRepository = Mock(UserPasswordTokenRepository)
    private UserPasswordTokenBO userPasswordTokenBO
    private UserBO userBO

    private UserPasswordTokenServiceImpl underTest

    def setup() {
        userPasswordTokenBO = Mock(UserPasswordTokenBO)
        userBO = Mock(UserBO)

        underTest = new UserPasswordTokenServiceImpl(userPasswordTokenRepository)

        userPasswordTokenBO.getUser() >> userBO
        userBO.getId() >> USER_ID
    }

    def "should save userPasswordTokenBO"() {
        when:
            underTest.save(userPasswordTokenBO)
        then:
            1 * userPasswordTokenRepository.save(userPasswordTokenBO)
    }

    def "should return UserPasswordTokenBO for a given UserId"() {
        when:
            UserPasswordTokenBO result = underTest.findByUserId(USER_ID)
        then:
            1 * userPasswordTokenRepository.findByUserId(USER_ID) >> Optional.of(userPasswordTokenBO)
            result == userPasswordTokenBO
    }

    def "should return new UserPasswordTokenBO when no token found for User"() {
        when:
            UserPasswordTokenBO result = underTest.findByUserId(USER_ID)
        then:
            1 * userPasswordTokenRepository.findByUserId(USER_ID) >> Optional.empty()
            result != null
    }

    def "should return Optional UserPasswordTokenBO for a given UserId and Token"() {
        when:
            Optional<UserPasswordTokenBO> result = underTest.findByUserIdAndToken(USER_ID, TOKEN)
        then:
            1 * userPasswordTokenRepository.findByUserIdAndToken(USER_ID, TOKEN) >> Optional.of(userPasswordTokenBO)
            result.isPresent()
            result.get() == userPasswordTokenBO
    }

    def "should delete UserPasswordTokenBO"() {
        when:
            underTest.delete(userPasswordTokenBO)
        then:
            1 * userPasswordTokenRepository.delete(userPasswordTokenBO)
    }
}