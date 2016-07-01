package com.cricketclub

import com.cricketclub.config.DBSetupNoScript
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.mock.web.MockServletContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import javax.servlet.ServletContext
import javax.sql.DataSource

@WebAppConfiguration
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = [ CricketClubAPIApplication.class, DBSetupNoScript.class ])
class CricketClubAPIApplicationTest extends Specification {

    @Autowired
    WebApplicationContext context

    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    def "should boot up without errors"() {
        expect: "web application context exists"
        context != null
    }
}