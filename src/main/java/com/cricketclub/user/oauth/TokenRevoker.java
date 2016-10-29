package com.cricketclub.user.oauth;

public interface TokenRevoker {
    void revoke(final String userId);
}
