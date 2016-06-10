package com.cricketclub.committee.member.service

import com.cricketclub.committee.member.dto.CommitteeMember
import com.cricketclub.committee.member.dto.CommitteeMemberList
import com.cricketclub.committee.member.domain.CommitteeMemberBO
import com.cricketclub.committee.role.domain.CommitteeRoleBO
import com.cricketclub.user.domain.UserBO
import com.cricketclub.committee.member.exception.CommitteeMemberAlreadyExistsException
import com.cricketclub.committee.member.exception.NoSuchCommitteeMemberException
import com.cricketclub.committee.role.exception.NoSuchCommitteeRoleException
import com.cricketclub.user.exception.NoSuchUserException
import com.cricketclub.committee.role.CommitteeRoleService

import spock.lang.Specification

import java.time.ZonedDateTime

class CommitteeMemberServiceImplTest extends Specification {

    private static final Long ID = 3
    private static final Long COMMITTEE_MEMBER_ID = 111
    private static Integer YEAR
    private static final Integer COMMITTEE_ROLE_ID = 1
    private static final Long USER_ID = 11

    private CommitteeMemberService committeeMemberService
    private CommitteeRoleService committeeRoleService
    private UserService userService
    private CommitteeMemberMapper committeeMemberMapper

    private CommitteeMemberList committeeMemberList
    private CommitteeMember committeeMember

    private CommitteeMemberBO committeeMemberBO
    private UserBO userBO
    private CommitteeRoleBO committeeRoleBO

    private CommitteeMemberServiceImpl underTest

    def setup() {
        committeeMemberService = Mock(CommitteeMemberService)
        committeeRoleService = Mock(CommitteeRoleService)
        userService = Mock(UserService)
        committeeMemberMapper = Mock(CommitteeMemberMapper)

        committeeMemberList = Mock(CommitteeMemberList)
        committeeMember = Mock(CommitteeMember)
        committeeMemberBO = Mock(CommitteeMemberBO)
        userBO = Mock(UserBO)
        committeeRoleBO = Mock(CommitteeRoleBO)

        underTest = new CommitteeMemberServiceImpl(
                committeeMemberRepository:committeeMemberService,
                committeeRoleService:committeeRoleService,
                userService:userService,
                mapper:committeeMemberMapper
        )

        YEAR = ZonedDateTime.now().getYear()
        committeeMember.getCommitteeMemberId() >> ID
        committeeMember.getUserId() >> USER_ID
        committeeMember.getCommitteeRoleId() >> COMMITTEE_ROLE_ID
        committeeMember.getYear() >> YEAR
    }

    def "test getLatestCommitteeMembers success"() {
        given:
            List<CommitteeMemberBO> committeeMemberBOList = new ArrayList<>()
            List<CommitteeMember> committeeMemberObjList = new ArrayList<>()
            committeeMemberObjList.add(committeeMember)
            committeeMemberBOList.add(committeeMemberBO)
        when:
            Optional<CommitteeMemberList> result = underTest.getLatestCommitteeMembers()
        then:
            1 * committeeMemberService.findByYear(YEAR) >> committeeMemberBOList
            1 * committeeMemberMapper.transform(committeeMemberBOList) >> committeeMemberObjList
            1 * committeeMemberMapper.transformToList(committeeMemberObjList) >> committeeMemberList
            result == Optional.of(committeeMemberList)
    }

    def "test getLatestCommitteeMembers when no committee member found"() {
        given:
            List<CommitteeMemberBO> committeeMemberBOList = new ArrayList<>()
            List<CommitteeMember> committeeMemberObjList = new ArrayList<>()
            committeeMemberObjList.add(committeeMember)
            committeeMemberBOList.add(committeeMemberBO)
        when:
            Optional<CommitteeMemberList> result = underTest.getLatestCommitteeMembers()
        then:
            1 * committeeMemberService.findByYear(YEAR) >> new ArrayList<>()
            1 * committeeMemberService.findByYear(YEAR - 1) >> new ArrayList<>()
            0 * committeeMemberMapper.transform(committeeMemberBOList) >> committeeMemberObjList
            0 * committeeMemberMapper.transformToList(committeeMemberObjList) >> committeeMemberList
            result == Optional.empty()
    }

    def "test addCommitteeMember success"() {
        when:
            underTest.addCommitteeMember(committeeMember)
        then:
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findById(USER_ID) >> Optional.of(userBO)
            1 * committeeMemberService.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            1 * committeeMemberMapper.transform(committeeMember) >> committeeMemberBO
            1 * committeeMemberService.save(committeeMemberBO)
    }

    def "test addCommitteeMember when committee role not found"() {
        when:
            underTest.addCommitteeMember(committeeMember)
        then:
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.empty()
            0 * userService.findById(USER_ID) >> Optional.of(userBO)
            0 * committeeMemberService.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            0 * committeeMemberMapper.transform(committeeMember) >> committeeMemberBO
            0 * committeeMemberService.save(committeeMemberBO)
            def ex = thrown(NoSuchCommitteeRoleException)
    }

    def "test addCommitteeMember when user not found"() {
        when:
            underTest.addCommitteeMember(committeeMember)
        then:
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findById(USER_ID) >> Optional.empty()
            0 * committeeMemberService.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            0 * committeeMemberMapper.transform(committeeMember) >> committeeMemberBO
            0 * committeeMemberService.save(committeeMemberBO)
            def ex = thrown(NoSuchUserException)
    }

    def "test addCommitteeMember when committee member already exists"() {
        when:
            underTest.addCommitteeMember(committeeMember)
        then:
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findById(USER_ID) >> Optional.of(userBO)
            1 * committeeMemberService.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.of(committeeMemberBO)
            0 * committeeMemberMapper.transform(committeeMember) >> committeeMemberBO
            0 * committeeMemberService.save(committeeMemberBO)
            def ex = thrown(CommitteeMemberAlreadyExistsException)
    }

    def "test updateCommitteeMember success"() {
        when:
            underTest.updateCommitteeMember(COMMITTEE_MEMBER_ID, committeeMember)
        then:
            1 * committeeMemberService.findById(COMMITTEE_MEMBER_ID) >> Optional.of(committeeMemberBO)
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findById(USER_ID) >> Optional.of(userBO)
            1 * committeeMemberService.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            1 * committeeMemberMapper.transform(committeeMember) >> committeeMemberBO
            1 * committeeMemberService.save(committeeMemberBO)
    }

    def "test updateCommitteeMember when committee Member not found"() {
        when:
            underTest.updateCommitteeMember(COMMITTEE_MEMBER_ID, committeeMember)
        then:
            1 * committeeMemberService.findById(COMMITTEE_MEMBER_ID) >> Optional.empty()
            0 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            0 * userService.findById(USER_ID) >> Optional.of(userBO)
            0 * committeeMemberService.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            0 * committeeMemberMapper.transform(committeeMember) >> committeeMemberBO
            0 * committeeMemberService.save(committeeMemberBO)
            def ex = thrown(NoSuchCommitteeMemberException)
    }

    def "test updateCommitteeMember when committee role not found"() {
        when:
            underTest.updateCommitteeMember(COMMITTEE_MEMBER_ID, committeeMember)
        then:
            1 * committeeMemberService.findById(COMMITTEE_MEMBER_ID) >> Optional.of(committeeMemberBO)
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.empty()
            0 * userService.findById(USER_ID) >> userBO
            0 * committeeMemberService.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            0 * committeeMemberMapper.transform(committeeMember) >> committeeMemberBO
            0 * committeeMemberService.save(committeeMemberBO)
            def ex = thrown(NoSuchCommitteeRoleException)
    }

    def "test updateCommitteeMember when user not found"() {
        when:
            underTest.updateCommitteeMember(COMMITTEE_MEMBER_ID, committeeMember)
        then:
            1 * committeeMemberService.findById(COMMITTEE_MEMBER_ID) >> Optional.of(committeeMemberBO)
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findById(USER_ID) >> Optional.empty()
            0 * committeeMemberService.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            0 * committeeMemberMapper.transform(committeeMember) >> committeeMemberBO
            0 * committeeMemberService.save(committeeMemberBO)
            def ex = thrown(NoSuchUserException)
    }

    def "test updateCommitteeMember when committee member already exists"() {
        when:
            underTest.updateCommitteeMember(COMMITTEE_MEMBER_ID, committeeMember)
        then:
            1 * committeeMemberService.findById(COMMITTEE_MEMBER_ID) >> Optional.of(committeeMemberBO)
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findById(USER_ID) >> Optional.of(userBO)
            1 * committeeMemberService.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.of(committeeMemberBO)
            0 * committeeMemberMapper.transform(committeeMember) >> committeeMemberBO
            0 * committeeMemberService.save(committeeMemberBO)
            def ex = thrown(CommitteeMemberAlreadyExistsException)
    }

    def "test deleteCommitteeMember success"() {
        when:
            underTest.deleteCommitteeMember(COMMITTEE_MEMBER_ID)
        then:
            1 * committeeMemberService.findById(COMMITTEE_MEMBER_ID) >> Optional.of(committeeMemberBO)
            1 * committeeMemberService.remove(committeeMemberBO)
    }

    def "test deleteCommitteeMember when committee member not found"() {
        when:
            underTest.deleteCommitteeMember(COMMITTEE_MEMBER_ID)
        then:
            1 * committeeMemberService.findById(COMMITTEE_MEMBER_ID) >> Optional.empty()
            0 * committeeMemberService.remove(committeeMemberBO)
            def ex = thrown(NoSuchCommitteeMemberException)
    }
}