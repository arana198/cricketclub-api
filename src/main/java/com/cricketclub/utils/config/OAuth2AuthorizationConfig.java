package com.cricketclub.utils.config;

import com.cricketclub.user.oauth.UserAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserAuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public OAuth2RequestFactory getOAuth2RequestFactory(){
        DefaultOAuth2RequestFactory defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
        return defaultOAuth2RequestFactory;
    }

    @Bean
    public UserApprovalHandler getApprovalHandler(){
        TokenStoreUserApprovalHandler tokenApprovalStore = new TokenStoreUserApprovalHandler();
        tokenApprovalStore.setTokenStore(tokenStore());
        tokenApprovalStore.setRequestFactory(getOAuth2RequestFactory());
        return tokenApprovalStore;
    }
    @Bean
    public OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint(){
        OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
        entryPoint.setTypeName("Basic");
        return entryPoint;
    }

    @Bean
    public JdbcApprovalStore getJdbcApprovalStore() {
        JdbcApprovalStore jdbcApprovalStore = new JdbcApprovalStore(dataSource);
        return jdbcApprovalStore;
    }

    @Bean
    public OAuth2AccessDeniedHandler oauthAccessDeniedHandler(){
        return new OAuth2AccessDeniedHandler();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints = endpoints.userApprovalHandler(getApprovalHandler());
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(new DefaultAccessTokenConverter())
                .approvalStore(getJdbcApprovalStore())
                .tokenStore(tokenStore());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("hasRole('ROLE_RESOURCE_SERVER')")
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }
}