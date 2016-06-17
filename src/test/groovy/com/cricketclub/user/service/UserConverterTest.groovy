package com.cricketclub.user.service

import com.cricketclub.user.dto.Role
import com.cricketclub.user.dto.RoleList
import com.cricketclub.user.dto.User
import com.cricketclub.user.dto.UserList
import com.cricketclub.profile.domain.UserProfileBO
import com.cricketclub.user.domain.RoleBO
import com.cricketclub.user.domain.UserBO
import spock.lang.Specification

class UserConverterTest extends Specification {

    private static final Integer USER_ID = 912
    private static final String USERNAME = "test"
    private static final String PASSWORD = "password"

    private UserBO userBO
    private RoleBO roleBO
    private User user
    private RoleList roleList

    private RoleConverter roleConverter = Mock(RoleConverter)

    private UserConverter underTest

    def setup() {
        userBO = Mock(UserBO)
        roleBO = Mock(RoleBO)
        user = Mock(User)
        roleList = Mock(RoleList)

        underTest = new UserConverter(roleConverter)
    }

    def "should return user when successfully converted"() {
        given:
            Set<RoleBO> roleBOSet = Arrays.asList(roleBO)
            userBO.getId() >> USER_ID
            userBO.getUsername() >> USERNAME
            userBO.getRoles() >> roleBOSet
        when:
            User result = underTest.convert(userBO)
        then:
            1 * roleConverter.convert(roleBOSet) >> roleList
            result != null
            result.getUserId() == USER_ID
            result.getUsername() == USERNAME
            result.getRoles() == roleList
    }

    def "should return UserList when converting List<UserBO>"() {
        given:
            Set<RoleBO> roleBOSet = Arrays.asList(roleBO)
            userBO.getId() >> USER_ID
            userBO.getUsername() >> USERNAME
            userBO.getRoles() >> roleBOSet
            List<UserBO> userBOList = Arrays.asList(userBO)
        when:
            UserList result = underTest.convert(userBOList)
        then:
            result != null
            result.getUsers().size() == 1
    }

    def "should return UserBO when converting User"() {
        given:
            user.getUsername() >> USERNAME
            user.getPassword() >> PASSWORD
        when:
            UserBO result = underTest.convert(user)
        then:
            result != null
            result.getPassword() == PASSWORD
            result.getUsername() == USERNAME
    }
}