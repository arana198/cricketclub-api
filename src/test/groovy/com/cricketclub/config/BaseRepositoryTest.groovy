package com.cricketclub.config

import com.cricketclub.CricketClubAPIApplication
import com.cricketclub.utils.config.OAuth2AuthorizationConfig
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.DbSetupTracker
import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.operation.Operation
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification

import javax.sql.DataSource

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = [ CricketClubAPIApplication.class, DBSetup.class ])
public abstract class BaseRepositoryTest extends Specification {

    protected static final DbSetupTracker DB_SETUP_TRACKER = new DbSetupTracker();

    @Autowired
    protected DataSource dataSource
}
