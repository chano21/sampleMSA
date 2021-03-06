package com.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.auth.service.AuthService;

/**
 * @author chano
 * @Date : 2021. 5. 16.
 * @Description : oauth2.0 ????????????
 */

//@Configuration
//@EnableAuthorizationServer
public class AuthConfiguration_test extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
    throws Exception {
        // ?????? ?????? endpoint??? ?????? ????????? ????????????. 
        super.configure(endpoints);
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
        .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // oauth_client_details ???????????? ????????? ???????????? ???????????????.
        clients.withClientDetails(clientDetailsService);
    }

    @Bean
    @Primary
    public JdbcClientDetailsService JdbcClientDetailsService(DataSource dataSource) {
        // Jdbc(H2 ??????????????????)??? ????????? Oauth client ??????????????? ?????? ???????????????.
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        // JWT key-value ????????? ???????????? ?????? ???????????????.
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(resourceServerProperties.getJwt().getKeyValue());

        return accessTokenConverter;
    }
}