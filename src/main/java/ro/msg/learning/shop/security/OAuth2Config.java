package ro.msg.learning.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    private int expiration = 3600;

    /**
     * this method hooks up the users into the auth server
     *
     * @param configurer AuthorizationServerEndpointsConfigurer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
        configurer.authenticationManager(authenticationManager)
                .tokenStore(tokenStore);
    }

    //TODO: this configure details are not read from application.yml?
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("someclient")
                .secret("$2a$04$T1Vbbwtfb0j73Ti0uKuu4ORGi2tm.EX6VSMOj9PeRsxv3PMrLkPCa")
                .accessTokenValiditySeconds(expiration)
                .scopes("read", "write", "trust")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .resourceIds("resource");
    }

}
