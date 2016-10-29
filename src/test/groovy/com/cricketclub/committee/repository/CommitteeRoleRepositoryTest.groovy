package com.cricketclub.committee.repository

import com.cricketclub.committee.domain.CommitteeRoleBO
import com.cricketclub.committee.dto.CommitteeRole
import com.cricketclub.config.BaseRepositoryTest
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.operation.Operation
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import static com.ninja_squad.dbsetup.Operations.sql
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf

class CommitteeRoleRepositoryTest extends BaseRepositoryTest {
    private static final Operation INSERT_REFERENCE_DATA =
            sequenceOf(sql("UPDATE committee_role SET visible = false WHERE ID = 10"))

    @Shared
    private static DbSetup DBSETUP

    @Autowired
    private CommitteeRoleRepository underTest

    def setup() {
        if(DBSETUP == null) {
            DBSETUP = new DbSetup(new DataSourceDestination(dataSource), INSERT_REFERENCE_DATA)
            DB_SETUP_TRACKER.launchIfNecessary(DBSETUP)
        }
    }

    def "should return committee role with id 1"() {
        when:
            Optional<CommitteeRoleBO> result = underTest.findById(1)
        then:
            result.isPresent()
            CommitteeRoleBO committeeRoleBO = result.get()
            committeeRoleBO.id == 1
    }

    def "should return committee role for a given role name"() {
        given:
            String roleName = "PRESIDENT"
        when:
            Optional<CommitteeRoleBO> result = underTest.findByName(roleName)
        then:
            result.isPresent()
            CommitteeRoleBO committeeRoleBO = result.get()
            committeeRoleBO.id == 1
            committeeRoleBO.name == roleName
    }

    def "should return hidden committee role when finding by not selectable"() {
        when:
            List<CommitteeRoleBO> result = underTest.findByVisible(false)
        then:
            result.size() != 0
            CommitteeRoleBO committeeRoleBO = result.get(0)
            committeeRoleBO.visible == false
    }
}