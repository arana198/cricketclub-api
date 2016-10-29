package com.cricketclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cricketclub.*.repository")
@EnableTransactionManagement
public class CricketClubAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(CricketClubAPIApplication.class, args);
    }
}
