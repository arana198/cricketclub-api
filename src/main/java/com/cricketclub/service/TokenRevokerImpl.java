package com.cricketclub.service;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Service;

@Service
class TokenRevokerImpl implements TokenRevoker{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenRevokerImpl.class);
	
	@Autowired
	private JdbcTokenStore jdbcTokenStore;
	
	public void revoke(final String userId){
		Collection<OAuth2AccessToken> accessTokenList = jdbcTokenStore.findTokensByUserName(userId);
		accessTokenList.stream().forEach(token -> { 
			LOGGER.debug("Removing token {} for user {}", token.getValue(), userId);
			jdbcTokenStore.removeAccessToken(token);
		});
	}
}
