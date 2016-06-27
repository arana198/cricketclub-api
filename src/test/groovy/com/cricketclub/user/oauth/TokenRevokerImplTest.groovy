package com.cricketclub.user.oauth

import com.cricketclub.user.oauth.TokenRevokerImpl
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import spock.lang.Specification


class TokenRevokerImplTest extends Specification {

    private static final String USER_ID = "2"
    private static final String TOKEN_VALUE = "value"

    private JdbcTokenStore jdbcTokenStore
    private OAuth2AccessToken oAuth2AccessToken

    private TokenRevokerImpl underTest

    def setup() {
        jdbcTokenStore = Mock(JdbcTokenStore)
        oAuth2AccessToken = Mock(OAuth2AccessToken)

        underTest = new TokenRevokerImpl(jdbcTokenStore);
    }

    def "should revoke token"() {
        when:
            underTest.revoke(USER_ID)
        then:
            oAuth2AccessToken.getValue() >> TOKEN_VALUE
            1 * jdbcTokenStore.findTokensByUserName(USER_ID) >> Arrays.asList(oAuth2AccessToken)
            1 * jdbcTokenStore.removeAccessToken(oAuth2AccessToken)
    }
}