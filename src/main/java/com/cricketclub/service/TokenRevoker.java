package com.cricketclub.service;

import org.springframework.stereotype.Service;

public interface TokenRevoker {
	void revoke(final String userId);
}
