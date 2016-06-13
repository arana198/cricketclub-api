package com.cricketclub.committee.service

import com.cricketclub.committee.dto.CommitteeMember
import com.cricketclub.committee.dto.CommitteeMemberList
import com.cricketclub.committee.domain.CommitteeMemberBO
import com.cricketclub.committee.repository.CommitteeMemberRepository
import com.cricketclub.committee.domain.CommitteeRoleBO
import com.cricketclub.user.domain.UserBO
import com.cricketclub.committee.exception.CommitteeMemberAlreadyExistsException
import com.cricketclub.committee.exception.NoSuchCommitteeMemberException
import com.cricketclub.committee.exception.NoSuchCommitteeRoleException
import com.cricketclub.user.dto.User
import com.cricketclub.user.exception.NoSuchUserException
import com.cricketclub.user.service.UserService
import spock.lang.Specification

import java.time.ZonedDateTime

class CommitteeMemberServiceImplTest extends Specification {

    private static final Long ID = 3
    private static final Long COMMITTEE_MEMBER_ID = 111
    private static Integer YEAR
    private static final Integer COMMITTEE_ROLE_ID = 1
    private static final Long USER_ID = 11

    private CommitteeMemberRepository committeeMemberRepository = Mock(CommitteeMemberRepository)
    private CommitteeRoleService committeeRoleService = Mock(CommitteeRoleService)
    private UserService userService  = Mock(UserService)
    private CommitteeMemberConverter committeeMemberConverter = Mock(CommitteeMemberConverter)

    private CommitteeMemberList committeeMemberList
    private CommitteeMember committeeMember

    private CommitteeMemberBO committeeMemberBO
    private User user
    private CommitteeRoleBO committeeRoleBO

    private CommitteeMemberServiceImpl underTest

    def setup() {
        committeeMemberList = Mock(CommitteeMemberList)
        committeeMember = Mock(CommitteeMember)
        committeeMemberBO = Mock(CommitteeMemberBO)
        user = Mock(User)
        committeeRoleBO = Mock(CommitteeRoleBO)

        underTest = new CommitteeMemberServiceImpl(
                committeeMemberRepository,
                committeeRoleService,
                userService,
                committeeMemberConverter
        )

        YEAR = ZonedDateTime.now().getYear()
        committeeMember.getCommitteeMemberId() >> ID
        committeeMember.getUserId() >> USER_ID
        committeeMember.getCommitteeRoleId() >> COMMITTEE_ROLE_ID
        committeeMember.getYear() >> YEAR
    }

    def "should return latest committee members"() {
        given:
            List<CommitteeMemberBO> committeeMemberBOList = new ArrayList<>()
            committeeMemberBOList.add(committeeMemberBO)
        when:
            Optional<CommitteeMemberList> result = underTest.getLatestCommitteeMembers()
        then:
            1 * committeeMemberRepository.findByYear(YEAR) >> committeeMemberBOList
            1 * committeeMemberConverter.convert(committeeMemberBOList) >> committeeMemberList
            result == Optional.of(committeeMemberList)
    }

    def "should return previous year committee members when there is non for current year"() {
        given:
            List<CommitteeMemberBO> committeeMemberBOList = new ArrayList<>()
            committeeMemberBOList.add(committeeMemberBO)
        when:
            Optional<CommitteeMemberList> result = underTest.getLatestCommitteeMembers()
        then:
            1 * committeeMemberRepository.findByYear(YEAR) >> new ArrayList<>()
            1 * committeeMemberRepository.findByYear(YEAR - 1) >> committeeMemberBOList
            1 * committeeMemberConverter.convert(committeeMemberBOList) >> committeeMemberList
            result == Optional.of(committeeMemberList)
    }

    def "should return empty optional when no committee member found"() {
        given:
            List<CommitteeMemberBO> committeeMemberBOList = new ArrayList<>()
            committeeMemberBOList.add(committeeMemberBO)
        when:
            Optional<CommitteeMemberList> result = underTest.getLatestCommitteeMembers()
        then:
            1 * committeeMemberRepository.findByYear(YEAR) >> new ArrayList<>()
            1 * committeeMemberRepository.findByYear(YEAR - 1) >> new ArrayList<>()
            0 * committeeMemberConverter.convert(committeeMemberBOList) >> committeeMemberList
            result == Optional.empty()
    }

    def "should add committee member"() {
        when:
            underTest.addCommitteeMember(committeeMember)
        then:
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findUserId(USER_ID) >> Optional.of(user)
            1 * committeeMemberRepository.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            1 * committeeMemberConverter.convert(committeeMember) >> committeeMemberBO
            1 * committeeMemberRepository.save(committeeMemberBO)
    }

    def "should throw NoSuchCommitteeRoleException when role not found"() {
        when:
            underTest.addCommitteeMember(committeeMember)
        then:
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.empty()
            0 * userService.findUserId(USER_ID) >> Optional.of(user)
            0 * committeeMemberRepository.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            0 * committeeMemberConverter.convert(committeeMember) >> committeeMemberBO
            0 * committeeMemberRepository.save(committeeMemberBO)
            def ex = thrown(NoSuchCommitteeRoleException)
    }

    def "should throw NoSuchUserException when user not found"() {
        when:
            underTest.addCommitteeMember(committeeMember)
        then:
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findUserId(USER_ID) >> Optional.empty()
            0 * committeeMemberRepository.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            0 * committeeMemberConverter.convert(committeeMember) >> committeeMemberBO
            0 * committeeMemberRepository.save(committeeMemberBO)
            def ex = thrown(NoSuchUserException)
    }

    def "should throw CommitteeMemberAlreadyExistsException when committee member already exists"() {
        when:
            underTest.addCommitteeMember(committeeMember)
        then:
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findUserId(USER_ID) >> Optional.of(user)
            1 * committeeMemberRepository.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.of(committeeMemberBO)
            0 * committeeMemberConverter.convert(committeeMember) >> committeeMemberBO
            0 * committeeMemberRepository.save(committeeMemberBO)
            def ex = thrown(CommitteeMemberAlreadyExistsException)
    }

    def "should update committeeMember"() {
        when:
            underTest.updateCommitteeMember(COMMITTEE_MEMBER_ID, committeeMember)
        then:
            1 * committeeMemberRepository.findById(COMMITTEE_MEMBER_ID) >> Optional.of(committeeMemberBO)
            1 * userService.findUserId(USER_ID) >> Optional.of(user)
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * committeeMemberRepository.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            1 * committeeMemberConverter.convert(committeeMember) >> committeeMemberBO
            1 * committeeMemberRepository.save(committeeMemberBO)
    }

    def "should throw NoSuchCommitteeMemberException when committee Member not found"() {
        when:
            underTest.updateCommitteeMember(COMMITTEE_MEMBER_ID, committeeMember)
        then:
            1 * committeeMemberRepository.findById(COMMITTEE_MEMBER_ID) >> Optional.empty()
            0 * userService.findUserId(USER_ID) >> Optional.of(user)
            0 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            0 * committeeMemberRepository.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            0 * committeeMemberConverter.convert(committeeMember) >> committeeMemberBO
            0 * committeeMemberRepository.save(committeeMemberBO)
            def ex = thrown(NoSuchCommitteeMemberException)
    }

    def "should throw NoSuchCommitteeRoleException when committee role not found"() {
        when:
            underTest.updateCommitteeMember(COMMITTEE_MEMBER_ID, committeeMember)
        then:
            1 * committeeMemberRepository.findById(COMMITTEE_MEMBER_ID) >> Optional.of(committeeMemberBO)
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.empty()
            0 * userService.findUserId(USER_ID) >> Optional.of(user)
            0 * committeeMemberRepository.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            0 * committeeMemberConverter.convert(committeeMember) >> committeeMemberBO
            0 * committeeMemberRepository.save(committeeMemberBO)
            def ex = thrown(NoSuchCommitteeRoleException)
    }

    def "updateCommitteeMember should throw NoSuchUserException when user not found"() {
        when:
            underTest.updateCommitteeMember(COMMITTEE_MEMBER_ID, committeeMember)
        then:
            1 * committeeMemberRepository.findById(COMMITTEE_MEMBER_ID) >> Optional.of(committeeMemberBO)
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findUserId(USER_ID) >> Optional.empty()
            0 * committeeMemberRepository.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.empty()
            0 * committeeMemberConverter.convert(committeeMember) >> committeeMemberBO
            0 * committeeMemberRepository.save(committeeMemberBO)
            def ex = thrown(NoSuchUserException)
    }

    def "updateCommitteeMember should throw CommitteeMemberAlreadyExistsException when committee member already exists"() {
        when:
            underTest.updateCommitteeMember(COMMITTEE_MEMBER_ID, committeeMember)
        then:
            1 * committeeMemberRepository.findById(COMMITTEE_MEMBER_ID) >> Optional.of(committeeMemberBO)
            1 * committeeRoleService.findById(COMMITTEE_ROLE_ID) >> Optional.of(committeeRoleBO)
            1 * userService.findUserId(USER_ID) >> Optional.of(user)
            1 * committeeMemberRepository.findByCommitteeRoleAndYear(COMMITTEE_ROLE_ID, YEAR) >> Optional.of(committeeMemberBO)
            0 * committeeMemberConverter.convert(committeeMember) >> committeeMemberBO
            0 * committeeMemberRepository.save(committeeMemberBO)
            def ex = thrown(CommitteeMemberAlreadyExistsException)
    }

    def "should delete committeeMember"() {
        when:
            underTest.deleteCommitteeMember(COMMITTEE_MEMBER_ID)
        then:
            1 * committeeMemberRepository.findById(COMMITTEE_MEMBER_ID) >> Optional.of(committeeMemberBO)
            1 * committeeMemberRepository.delete(committeeMemberBO)
    }

    def "should throw NoSuchCommitteeMemberException when committee member not found"() {
        when:
            underTest.deleteCommitteeMember(COMMITTEE_MEMBER_ID)
        then:
            1 * committeeMemberRepository.findById(COMMITTEE_MEMBER_ID) >> Optional.empty()
            0 * committeeMemberRepository.remove(committeeMemberBO)
            def ex = thrown(NoSuchCommitteeMemberException)
    }
}