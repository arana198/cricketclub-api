package com.cricketclub.user.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
class TokenRevokerImpl implements TokenRevoker {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenRevokerImpl.class);

    private final JdbcTokenStore jdbcTokenStore;

    @Autowired
    public TokenRevokerImpl(TokenStore tokenStore) {
        this.jdbcTokenStore = (JdbcTokenStore) tokenStore;
    }

    public void revoke(final String userId) {
        Collection<OAuth2AccessToken> accessTokenList = jdbcTokenStore.findTokensByUserName(userId);
        accessTokenList.stream().forEach(token -> {
            LOGGER.debug("Removing token {} for service {}", token.getValue(), userId);
            jdbcTokenStore.removeAccessToken(token);
        });
    }
}
