package ro.msg.learning.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    public static final String RESOURCE_ID = "resource_id";
    @Value("${oauth.token.timeout}")
    private int expiration;
    @Value("${oauth.client.name}")
    private String client;
    @Value("${oauth.client.secret}")
    private String client_secret;
    @Value("${oauth.client.scopes}")
    private String[] client_scopes;
    @Value("${oauth.grant.types}")
    private String[] grant_types;

    /**
     * this method hooks up the users into the auth server
     *
     * @param configurer AuthorizationServerEndpointsConfigurer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
        configurer.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(client)
                .secret(client_secret)
                .accessTokenValiditySeconds(expiration)
                .scopes(client_scopes)
                .authorizedGrantTypes(grant_types)
                .resourceIds(RESOURCE_ID);
    }
}