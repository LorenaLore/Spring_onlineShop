package ro.msg.learning.shop.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;

public class UserAuthenticationManager {

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new OAuth2AuthenticationManager().authenticate(authentication);
    }
}
