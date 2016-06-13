package com.cricketclub.committee.service

import com.cricketclub.committee.dto.CommitteeMember
import com.cricketclub.committee.dto.CommitteeMemberList
import com.cricketclub.committee.dto.CommitteeRole
import com.cricketclub.committee.domain.CommitteeMemberBO
import com.cricketclub.committee.domain.CommitteeRoleBO
import spock.lang.Specification

class CommitteeMemberConverterTest extends Specification {

    private static final Long COMMITTEE_MEMBER_ID = 1
    private static final Integer YEAR = 2013
    private static final Long USER_ID = 99
    private static final Integer COMMITTEE_ROLE_ID = 912

    private CommitteeRoleConverter committeeRoleConverter = Mock(CommitteeRoleConverter)

    private CommitteeMemberBO committeeMemberBO
    private CommitteeRoleBO committeeRoleBO

    private CommitteeRole committeeRole
    private CommitteeMember committeeMember

    private CommitteeMemberConverter underTest

    def setup() {
        committeeMemberBO = Mock(CommitteeMemberBO)
        committeeRoleBO = Mock(CommitteeRoleBO)

        committeeRole = Mock(CommitteeRole)
        committeeMember = Mock(CommitteeMember)

        underTest = new CommitteeMemberConverter(committeeRoleConverter)

        committeeMemberBO.getId() >> COMMITTEE_MEMBER_ID
        committeeMemberBO.getYear() >> YEAR
        committeeMemberBO.getUserId() >> USER_ID
        committeeMemberBO.getCommitteeRole() >> committeeRoleBO

        committeeRoleBO.getId() >> COMMITTEE_ROLE_ID
    }

    def "should convert to committeeMemberBO to committeeMember "() {
        when:
            CommitteeMember result = underTest.convert(committeeMemberBO)
        then:
            1 * committeeRoleConverter.convert(committeeRoleBO) >> committeeRole
            result != null
            result.getYear() == YEAR
            result.getCommitteeRole() == committeeRole
            result.getCommitteeRoleId() == COMMITTEE_ROLE_ID
            result.getCommitteeMemberId() == COMMITTEE_MEMBER_ID
            result.getUserId() == USER_ID
    }

    def "should convert to CommitteeMemberList"() {
        given:
            List<CommitteeMemberBO> committeeMemberBOList = new ArrayList<>()
            committeeMemberBOList.add(committeeMemberBO)
        when:
            CommitteeMemberList result = underTest.convert(committeeMemberBOList)
        then:
            result != null
            result.getCommitteeMembers().size() == 1
    }

    def "should convert to committeeMemberBO"() {
        given:
            committeeMember.getYear() >> YEAR
        when:
            CommitteeMemberBO result = underTest.convert(committeeMember)
        then:
            result != null
            result.getYear() == YEAR
    }
}