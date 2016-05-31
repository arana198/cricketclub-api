package com.cricketclub.facade.committee

import com.cricketclub.api.resource.committee.CommitteeMember
import com.cricketclub.api.resource.committee.CommitteeMemberList
import com.cricketclub.api.resource.committee.CommitteeRole
import com.cricketclub.api.resource.user.User
import com.cricketclub.domain.committee.CommitteeMemberBO
import com.cricketclub.domain.committee.CommitteeRoleBO
import com.cricketclub.domain.user.UserBO
import com.cricketclub.facade.user.UserMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class CommitteeMemberMapperTest extends Specification {

    private static final Long COMMITTEE_MEMBER_ID = 1
    private static final Integer YEAR = 2013
    private static final Long USER_ID = 99
    private static final Integer COMMITTEE_ROLE_ID = 912

    private CommitteeRoleMapper committeeRoleMapper
    private UserMapper userMapper

    private UserBO userBO
    private CommitteeMemberBO committeeMemberBO
    private CommitteeRoleBO committeeRoleBO

    private User user
    private CommitteeRole committeeRole
    private CommitteeMember committeeMember

    private CommitteeMemberMapper underTest

    def setup() {
        committeeMemberBO = Mock(CommitteeMemberBO)
        userBO = Mock(UserBO)
        committeeRoleBO = Mock(CommitteeRoleBO)

        user = Mock(User)
        committeeRole = Mock(CommitteeRole)
        committeeMember = Mock(CommitteeMember)

        userMapper = Mock(UserMapper)
        committeeRoleMapper = Mock(CommitteeRoleMapper)
        underTest = Mappers.getMapper( CommitteeMemberMapper.class )
        underTest.committeeRoleMapper = committeeRoleMapper
        underTest.userMapper = userMapper

        committeeMemberBO.getId() >> COMMITTEE_MEMBER_ID
        committeeMemberBO.getYear() >> YEAR
        committeeMemberBO.getUser() >> userBO
        committeeMemberBO.getCommitteeRole() >> committeeRoleBO

        userBO.getId() >> USER_ID
        committeeRoleBO.getId() >> COMMITTEE_ROLE_ID
    }

    def "test transform to committeeMember success"() {
        when:
            CommitteeMember result = underTest.transform(committeeMemberBO)
        then:
            1 * committeeRoleMapper.transform(committeeRoleBO) >> committeeRole
            1 * userMapper.transform(userBO) >> user
            result != null
            result.getYear() == YEAR
            result.getCommitteeRole() == committeeRole
            result.getUser() == user
            result.getCommitteeRoleId() == COMMITTEE_ROLE_ID
            result.getCommitteeMemberId() == COMMITTEE_MEMBER_ID
            result.getUserId() == USER_ID
    }

    def "test transform to committeeMember when null input"() {
        when:
            CommitteeMember result = underTest.transform(null)
        then:
            0 * committeeRoleMapper.transform(committeeRoleBO) >> committeeRole
            0 * userMapper.transform(userBO) >> user
            result == null
    }

    def "test transform to committeeMemberList success"() {
        given:
            List<CommitteeMemberBO> committeeMemberBOList = new ArrayList<>()
            committeeMemberBOList.add(committeeMemberBO)
        when:
            List<CommitteeMember> result = underTest.transform(committeeMemberBOList)
        then:
            result.size() == 1
    }

    def "test transform to committeeMemberList when null input"() {
        when:
            List<CommitteeMember> result = underTest.transform(null)
        then:
            result == null
    }

    def "test transformToList success"() {
        given:
            List<CommitteeMember> committeeMembersList = Arrays.asList(committeeMember)
        when:
            CommitteeMemberList result = underTest.transformToList(committeeMembersList)
        then:
            result != null
            result.getCommitteeMembers().size() == 1
    }
}