package com.cricketclub.config

import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.mock.web.MockServletContext
import org.springframework.transaction.PlatformTransactionManager

import javax.servlet.ServletContext
import javax.sql.DataSource

public class DBSetupNoScript {

    @Bean
    public PlatformTransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public ServletContext getServletContext() {
        return new MockServletContext()
    }
}
