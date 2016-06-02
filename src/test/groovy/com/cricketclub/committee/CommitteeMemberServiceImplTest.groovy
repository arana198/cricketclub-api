package com.cricketclub.committee

import com.cricketclub.committee.member.service.CommitteeMemberServiceImpl
import com.cricketclub.committee.member.domain.CommitteeMemberBO
import com.cricketclub.committee.role.domain.CommitteeRoleBO
import com.cricketclub.user.domain.UserBO
import com.cricketclub.committee.member.repository.CommitteeMemberRepository
import spock.lang.Specification

class CommitteeMemberServiceImplTest extends Specification {

    private static final Integer ID = 1
    private static final Integer YEAR = 2014;
    private static final CommitteeRoleBO.CommitteeRole COMMITTEE_ROLE = CommitteeRoleBO.CommitteeRole.CHAIRMAN

    private CommitteeMemberRepository committeeMemberRepository
    private CommitteeMemberBO committeeMemberBO
    private CommitteeRoleBO committeeRoleBO
    private UserBO userBO

    private CommitteeMemberServiceImpl underTest

    def setup() {
        committeeMemberRepository = Mock(CommitteeMemberRepository)
        committeeMemberBO = Mock(CommitteeMemberBO)
        userBO = Mock(UserBO)
        committeeRoleBO = Mock(CommitteeRoleBO)

        underTest = new CommitteeMemberServiceImpl(committeeMemberRepository:committeeMemberRepository);
    }

    def "test findById"() {
        when:
            Optional<CommitteeMemberBO> result = underTest.findById(ID)
        then:
            1 * committeeMemberRepository.findOne(ID) >> committeeMemberBO
            result.isPresent()
            result.get() == committeeMemberBO
    }

    def "test findByYear"() {
        given:
            List<CommitteeRoleBO> committeeRoleBOList = new ArrayList<>();
            committeeRoleBOList.add(committeeMemberBO)
        when:
            List<CommitteeRoleBO> result = underTest.findByYear(YEAR)
        then:
            1 * committeeMemberRepository.findByYear(YEAR) >> committeeRoleBOList
            result.size() == 1
            result.get(0) == committeeMemberBO
    }

    def "test findByUser"() {
        given:
            List<CommitteeRoleBO> committeeRoleBOList = new ArrayList<>();
            committeeRoleBOList.add(committeeMemberBO)
        when:
            List<CommitteeRoleBO> result = underTest.findByUser(userBO)
        then:
            1 * committeeMemberRepository.findByUser(userBO) >> committeeRoleBOList
            result.size() == 1
            result.get(0) == committeeMemberBO
    }

    def "test findByCommitteeRoleAndYear"() {
        when:
            Optional<CommitteeMemberBO> result = underTest.findByCommitteeRoleAndYear(ID, YEAR)
        then:
            1 * committeeMemberRepository.findByCommitteeRoleAndYear(ID, YEAR) >> Optional.of(committeeMemberBO)
            result.isPresent()
            result.get() == committeeMemberBO
    }

    def "test save"() {
        when:
            underTest.save(committeeMemberBO)
        then:
            userBO.getId() >> ID
            committeeRoleBO.getId() >> ID
            committeeMemberBO.getUser() >> userBO
            committeeMemberBO.getCommitteeRole() >> committeeRoleBO
            1 * committeeMemberRepository.save(committeeMemberBO)
    }

    def "test remove"() {
        when:
            underTest.remove(committeeMemberBO)
        then:
            1 * committeeMemberRepository.delete(committeeMemberBO)
    }
}