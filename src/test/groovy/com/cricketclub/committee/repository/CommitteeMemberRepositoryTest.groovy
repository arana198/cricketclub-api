package com.cricketclub.committee.repository

import com.cricketclub.committee.domain.CommitteeMemberBO
import com.cricketclub.config.BaseRepositoryTest
import com.cricketclub.user.repository.UserRepository
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.operation.Operation
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import java.time.LocalDate

import static com.ninja_squad.dbsetup.Operations.insertInto
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf

class CommitteeMemberRepositoryTest extends BaseRepositoryTest {
    private static final Operation INSERT_REFERENCE_DATA =
            sequenceOf(insertInto("ELECTED_OFFICERS")
                            .columns("id", "user_id", "committee_role_id", "year")
                            .values(1, 1, 1, LocalDate.now().getYear())
                            .values(2, 1, 1,  LocalDate.now().getYear() - 1)
                            .values(3, 2, 4,  LocalDate.now().getYear())
                            .build())

    @Shared
    private static DbSetup DBSETUP

    @Autowired
    private CommitteeMemberRepository underTest

    def setup() {
        if(DBSETUP == null) {
            DBSETUP = new DbSetup(new DataSourceDestination(dataSource), INSERT_REFERENCE_DATA)
            DB_SETUP_TRACKER.launchIfNecessary(DBSETUP)
        }
    }

    def "should return list of committee members for a given year"() {
        given:
            Integer year = LocalDate.now().getYear()
        when:
            List<CommitteeMemberBO> result = underTest.findByYear(year)
        then:
            result.size() == 2
            CommitteeMemberBO committeeMemberBO = result.get(0)
            committeeMemberBO.year == year
    }

    def "should return list of committee members for a given user id"() {
        given:
            Long userId = 1
        when:
            List<CommitteeMemberBO> result = underTest.findByUserId(userId)
        then:
            result.size() == 2
            CommitteeMemberBO committeeMemberBO = result.get(0)
            committeeMemberBO.userId == userId
    }

    def "should return optional for a given committee role and year"() {
        given:
        Integer committeeRoleId = 1
            Integer year = LocalDate.now().getYear()
        when:
            Optional<CommitteeMemberBO> result = underTest.findByCommitteeRoleAndYear(committeeRoleId, year)
        then:
            result.isPresent()
            CommitteeMemberBO committeeMemberBO = result.get()
            committeeMemberBO.committeeRole.id == committeeRoleId
            committeeMemberBO.year == year
    }

    def "should return empty optional when no committee members found for a given committee role and year"() {
        given:
        Integer committeeRoleId = 9
            Integer year = LocalDate.now().getYear()
        when:
            Optional<CommitteeMemberBO> result = underTest.findByCommitteeRoleAndYear(committeeRoleId, year)
        then:
            !result.isPresent()
    }

    def "should return optional for a given committee member id"() {
        given:
            Long committeeMemberId = 1
        when:
            Optional<CommitteeMemberBO> result = underTest.findById(committeeMemberId)
        then:
            result.isPresent()
            CommitteeMemberBO committeeMemberBO = result.get()
            committeeMemberBO.id == committeeMemberId
    }

    def "should return empty optional when committee member not found for a given committee member id"() {
        when:
            Optional<CommitteeMemberBO> result = underTest.findById(-1)
        then:
            !result.isPresent()
    }
}
