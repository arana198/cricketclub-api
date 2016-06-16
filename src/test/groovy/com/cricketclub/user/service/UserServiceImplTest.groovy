package com.cricketclub.user.service

import com.cricketclub.user.domain.RoleBO
import com.cricketclub.user.domain.UserBO
import com.cricketclub.user.domain.UserStatusBO
import com.cricketclub.user.dto.User
import com.cricketclub.user.exception.NoSuchRoleException
import com.cricketclub.user.exception.NoSuchUserException
import com.cricketclub.user.exception.UserAlreadyExistsException
import com.cricketclub.user.oauth.TokenRevoker
import com.cricketclub.user.repository.UserRepository
import spock.lang.Specification

import java.security.Principal

class UserServiceImplTest extends Specification {

    private static final Long ID = 2
    private static final Long USER_ID = 2
    private static final String USERNAME = "username"

    private UserRepository userRepository = Mock(UserRepository)
    private RoleService roleService = Mock(RoleService)
    private UserStatusService userStatusService = Mock(UserStatusService)
    private UserPasswordTokenService userPasswordTokenService = Mock(UserPasswordTokenService)
    private TokenRevoker tokenRevoker = Mock(TokenRevoker)
    private UserConverter userConverter = Mock(UserConverter)

    private User user
    private UserBO userBO
    private UserStatusBO userStatusBO
    private RoleBO roleBO

    private UserServiceImpl underTest

    def setup() {
        userBO = Mock(UserBO)
        userStatusBO = Mock(UserStatusBO)
        roleBO = Mock(RoleBO)

        user = Mock(User)

        underTest = new UserServiceImpl(
                userRepository,
                roleService,
                userStatusService,
                userPasswordTokenService,
                tokenRevoker,
                userConverter);
    }

    def "should return user for a given principal"() {
        given:
            Principal principal = Mock(Principal)
            principal.getName() >> USERNAME
        when:
            User result = underTest.me(principal)
        then:
            1 * userRepository.findByUsername(USERNAME) >> Optional.of(userBO)
            1 * userConverter.convert(userBO) >> user
            result == user
    }

    def "should return user for a given userId"() {
        when:
            Optional<User> result = underTest.findUserId(USER_ID)
        then:
            1 * userRepository.findById(USER_ID) >> Optional.of(userBO)
            1 * userConverter.convert(userBO) >> user
            result.isPresent()
            result.get() == user
    }

    def "should logout and remove token for a given principal"() {
        given:
            Principal principal = Mock(Principal)
            principal.getName() >> USERNAME
        when:
         User result = underTest.logout(principal)
        then:
            1 * userRepository.findByUsername(USERNAME) >> Optional.of(userBO)
            1 * tokenRevoker.revoke(USERNAME)
    }

    def "should throw an NoSuchUserException when user not found for a given principal"() {
        given:
            Principal principal = Mock(Principal)
            principal.getName() >> USERNAME
        when:
            User result = underTest.logout(principal)
        then:
            1 * userRepository.findByUsername(USERNAME) >> Optional.empty()
            0 * tokenRevoker.revoke(USERNAME)
            def ex = thrown(NoSuchUserException)
            ex.message == "User [ ${USERNAME} ] not found"
    }

    def "should create user"() {
        given:
            RoleBO.Role role = RoleBO.Role.ROLE_CAPTAIN
            user.getUsername() >> USERNAME
            UserBO userBOOj = new UserBO()
        when:
            underTest.createUser(user, role)
        then:
            1 * userRepository.findByUsername(USERNAME) >> Optional.empty()
            1 * userConverter.convert(user) >> userBOOj
            1 * userStatusService.findByName(UserStatusBO.UserStatus.PENDING) >> Optional.of(userStatusBO)
            1 * roleService.findByName(role) >> Optional.of(roleBO)
            1 * userRepository.save(userBOOj)
    }

    def "should throw UserAlreadyExistsException when username already exists"() {
        given:
            RoleBO.Role role = RoleBO.Role.ROLE_CAPTAIN
            user.getUsername() >> USERNAME
        when:
            underTest.createUser(user, role)
        then:
            1 * userRepository.findByUsername(USERNAME) >> Optional.of(userBO)
            0 * userConverter.convert(user) >> userBO
            0 * userStatusService.findByName(UserStatusBO.UserStatus.PENDING) >> Optional.of(userStatusBO)
            0 * roleService.findByName(role) >> Optional.of(roleBO)
            0 * userRepository.save(userBO)
            def ex = thrown(UserAlreadyExistsException)
            ex.message == "User [ ${USERNAME} ] already exists"
    }

    def "should throw NoSuchRoleException when role does not exist"() {
        given:
            RoleBO.Role role = RoleBO.Role.ROLE_CAPTAIN
            user.getUsername() >> USERNAME
            UserBO userBOOj = new UserBO()
        when:
            underTest.createUser(user, role)
        then:
            1 * userRepository.findByUsername(USERNAME) >> Optional.empty()
            1 * userConverter.convert(user) >> userBOOj
            1 * userStatusService.findByName(UserStatusBO.UserStatus.PENDING) >> Optional.of(userStatusBO)
            1 * roleService.findByName(role) >> Optional.empty()
            0 * userRepository.save(userBOOj)
            def ex = thrown(NoSuchRoleException)
            ex.message == "Role [ ${role} ] not found"
    }

    def "should update user"() {
        given:
            user.getUsername() >> USERNAME
            user.getUserId() >> ID
            userBO.getUsername() >> USERNAME + "2"
            UserBO userBOOj = new UserBO()
        when:
            underTest.updateUser(user)
        then:
            1 * userRepository.findById(ID) >> Optional.of(userBO)
            1 * userRepository.save(userBO)
    }

    def "should throw NoSuchUserException when userId does not exists"() {
        given:
            user.getUserId() >> ID
            UserBO userBOOj = new UserBO()
        when:
            underTest.updateUser(user)
        then:
            1 * userRepository.findById(ID) >> Optional.empty()
            0 * userRepository.save(userBO)
            def ex = thrown(NoSuchUserException)
            ex.message == "User [ ${ID} ] not found"
    }

    def "should ignore when username is not changed"() {
        given:
            user.getUserId() >> ID
            user.getUsername() >> USERNAME
            userBO.getUsername() >> USERNAME
            UserBO userBOOj = new UserBO()
        when:
            underTest.updateUser(user)
        then:
            1 * userRepository.findById(ID) >> Optional.of(userBO)
            0 * userRepository.save(userBO)
    }
}