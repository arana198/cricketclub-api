package com.cricketclub.team

import com.cricketclub.committee.domain.CommitteeMemberBO
import com.cricketclub.committee.repository.CommitteeMemberRepository
import com.cricketclub.config.BaseRepositoryTest
import com.cricketclub.team.domain.TeamBO
import com.cricketclub.team.repository.TeamRepository
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.operation.Operation
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared
import spock.lang.Unroll

import java.time.LocalDate

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom
import static com.ninja_squad.dbsetup.Operations.insertInto
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf

@Unroll
class TeamRepositoryTest extends BaseRepositoryTest{
    private static final Operation DELETE_ALL =  deleteAllFrom("TEAM")
    private static final Operation INSERT_REFERENCE_DATA =
            sequenceOf(DELETE_ALL,
                    insertInto("team")
                    .columns("id", "name", "description", "is_active")
                    .values(1, "1st XI", "First 11", true)
                    .values(2, "2nd XI", "Second 11", false)
                    .values(3, "3rd XI", "Third 11",  true)
                    .values(4, "4rd XI", "Fourth 11",  false)
                    .build())

    @Shared
    private static DbSetup DBSETUP

    @Autowired
    private TeamRepository underTest

    def setup() {
        if(DBSETUP == null) {
            DBSETUP = new DbSetup(new DataSourceDestination(dataSource), INSERT_REFERENCE_DATA)
            DB_SETUP_TRACKER.launchIfNecessary(DBSETUP)
        }
    }

    def "should return list of teams when team isActive is #isActive"() {
        when:
            List<TeamBO> result = underTest.findAllByActive(isActive)
        then:
            result.size() == 2
            TeamBO teamBO = result.get(0)
            teamBO.active == expectedResult
        where:
            isActive    |   expectedResult
            true        |   true
            false       |   false
    }

    def "should return team for a given id"() {
        given:
            Long id = 2
        when:
            Optional<TeamBO> result = underTest.findById(id)
        then:
            result.isPresent()
            TeamBO teamBO = result.get()
            teamBO.id == id
    }
}
