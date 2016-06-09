package com.cricketclub.committee.member

import com.cricketclub.committee.member.dto.CommitteeMember
import com.cricketclub.committee.member.dto.CommitteeMemberList
import com.cricketclub.committee.role.dto.CommitteeRole
import com.cricketclub.user.dto.User
import com.cricketclub.committee.member.domain.CommitteeMemberBO
import com.cricketclub.committee.role.domain.CommitteeRoleBO
import com.cricketclub.user.domain.UserBO
import com.cricketclub.user.service.mapper.UserConverter
import com.cricketclub.committee.role.service.CommitteeRoleConverter
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class CommitteeMemberConverterTest extends Specification {

    private static final Long COMMITTEE_MEMBER_ID = 1
    private static final Integer YEAR = 2013
    private static final Long USER_ID = 99
    private static final Integer COMMITTEE_ROLE_ID = 912

    private CommitteeRoleConverter committeeRoleMapper
    private UserConverter userMapper

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

        userMapper = Mock(UserConverter)
        committeeRoleMapper = Mock(CommitteeRoleConverter)
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