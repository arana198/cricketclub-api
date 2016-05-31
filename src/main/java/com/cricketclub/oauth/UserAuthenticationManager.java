package com.cricketclub.oauth;

import com.cricketclub.domain.user.UserBO;
import com.cricketclub.exception.user.UserAuthenticationException;
import com.cricketclub.repository.user.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthenticationManager implements AuthenticationManager{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        Object password = authentication.getCredentials();

        String encryptedPassword = DigestUtils.md5Hex(password.toString());
        UserBO userBO = userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(encryptedPassword))
                .orElseThrow(() -> new UserAuthenticationException("User authentication failed"));

        return new UsernamePasswordAuthenticationToken(userBO.getUsername(), userBO.getPassword(), userBO.getRoles());
    }

    public Optional<UserBO> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }
}
