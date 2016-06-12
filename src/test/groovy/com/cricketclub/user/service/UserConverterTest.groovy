package com.cricketclub.user.service

import com.cricketclub.user.dto.Role
import com.cricketclub.user.dto.User
import com.cricketclub.user.dto.UserList
import com.cricketclub.profile.domain.UserProfileBO
import com.cricketclub.user.domain.RoleBO
import com.cricketclub.user.domain.UserBO
import spock.lang.Specification

class UserConverterTest extends Specification {

    private static final Integer USER_ID = 912
    private static final String USERNAME = "test"
    private static final String HOME_NUMBER = "1234"
    private static final String MOBILE_NUMBER = "0789"
    private static final String FIRSTNAME = "first"
    private static final String LASTNAME = "last"

    private UserBO userBO
    private RoleBO roleBO
    private UserProfileBO userProfileBO
    private User user
    private Role role

    private RoleConverter roleConverter

    private UserConverter underTest

    def setup() {
        userBO = Mock(UserBO)
        roleBO = Mock(RoleBO)
        userProfileBO = Mock(UserProfileBO)
        user = Mock(User)
        role = Mock(Role)

        roleConverter = Mock(RoleConverter)

        underTest = new UserConverter(roleConverter:roleConverter)

        userBO.getId() >> USER_ID
        userBO.getUsername() >> USERNAME
        userBO.getUserProfile() >> userProfileBO
        userBO.getRoles() >> Arrays.asList(roleBO)
        userProfileBO.getFirstName() >> FIRSTNAME
        userProfileBO.getLastName() >> LASTNAME
        userProfileBO.getHomeNumber() >> HOME_NUMBER
        userProfileBO.getMobileNumber() >> MOBILE_NUMBER
    }

    def "test transform to user success"() {
        when:
            User result = underTest.convert(userBO)
        then:
            1 * roleMapper.transform(_) >> role
            result != null
            result.getUserId() == USER_ID
            result.getUsername() == USERNAME
            result.getFirstName() == FIRSTNAME
            result.getLastName() == LASTNAME
            result.getMobileNumber() == MOBILE_NUMBER
            result.getHomeNumber() == HOME_NUMBER
            result.getRoles().size() == 1
            result.getRoles().get(0) == role
    }

    def "test transform to user list success"() {
        given:
            List<UserBO> userBOList = Arrays.asList(userBO)
        when:
            List<User> result = underTest.convert(userBOList)
        then:
            result.size() == 1
    }

    def "test transformToList success"() {
        given:
            List<User> users = Arrays.asList(user)
        when:
            UserList result = underTest.convert(users)
        then:
            result != null
            result.getUsers().size() == 1
    }
}