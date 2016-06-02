package com.cricketclub.facade.user

import com.cricketclub.user.dto.Role
import com.cricketclub.user.dto.User
import com.cricketclub.user.dto.UserList
import com.cricketclub.common.domain.profile.UserProfileBO
import com.cricketclub.user.domain.RoleBO
import com.cricketclub.user.domain.UserBO
import com.cricketclub.user.service.mapper.UserMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class UserMapperTest extends Specification {

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

    private RoleMapper roleMapper

    private UserMapper underTest

    def setup() {
        userBO = Mock(UserBO)
        roleBO = Mock(RoleBO)
        userProfileBO = Mock(UserProfileBO)
        user = Mock(User)
        role = Mock(Role)

        roleMapper = Mock(RoleMapper)

        underTest = Mappers.getMapper( UserMapper.class )
        underTest.roleMapper = roleMapper

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
            User result = underTest.transform(userBO)
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

    def "test transform to User when null input"() {
        when:
            User result = underTest.transform(null)
        then:
            result == null
    }

    def "test transform to user list success"() {
        given:
            List<UserBO> userBOList = Arrays.asList(userBO)
        when:
            List<User> result = underTest.transform(userBOList)
        then:
            result.size() == 1
    }

    def "test transform to user list when null input"() {
        when:
            List<User> result = underTest.transform(null)
        then:
            result == null
    }

    def "test transformToList success"() {
        given:
            List<User> users = Arrays.asList(user)
        when:
            UserList result = underTest.transformToList(users)
        then:
            result != null
            result.getUsers().size() == 1
    }
}