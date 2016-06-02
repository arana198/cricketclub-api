package com.cricketclub.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled=true)
public class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration{

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER > ROLE_GUEST");
        return roleHierarchy;
    }

    @Bean
    public RoleVoter roleVoter(){
        return new RoleHierarchyVoter(roleHierarchy());
    }

    @Bean
    @Override
    protected AccessDecisionManager accessDecisionManager(){
        return new AffirmativeBased(Arrays.asList(roleVoter()));
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler(){
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy());
        return handler;
    }
}
