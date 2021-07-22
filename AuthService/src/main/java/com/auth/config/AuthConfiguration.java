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
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
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
 * @Description : oauth2.0 인가설정
 */

@Configuration
@EnableAuthorizationServer
public class AuthConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	AuthService authService;
	

	 @Autowired
	 private ResourceServerProperties resourceServerProperties;
	 

	 
	@Value("${security.oauth2.client.client-id}")
	private String client_id;

	@Value("${security.oauth2.client.client-secret}")
	private String client_secret;

	@Value("${spring.redis.host}")
	private String redisHost;
	
	@Value("${spring.redis.password}")
	private String redisPassword;
	
	@Value("${spring.redis.port}")
	private int redisPort;
	
	
	
	@Bean
	public TokenStore tokenStore() {
	return new RedisTokenStore(redisConnectionFactory());
	}
	  @Bean
	    public RedisConnectionFactory redisConnectionFactory() {
	        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	     
	        redisStandaloneConfiguration.setPassword(redisPassword);
	        redisStandaloneConfiguration.setPort(redisPort);
	        redisStandaloneConfiguration.setHostName(redisHost);
	        
	        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
	        return lettuceConnectionFactory;
	    }

	    @Bean
	    public WebResponseExceptionTranslator loggingExceptionTranslator() {
	        return new DefaultWebResponseExceptionTranslator() {
	            @Override
	            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
	                e.printStackTrace();
	                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
	                HttpHeaders headers = new HttpHeaders();
	                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
	                OAuth2Exception excBody = responseEntity.getBody();
	                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
	            }
	        };
	    }
	
	    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
    throws Exception {
        super.configure(endpoints);
        endpoints.accessTokenConverter(jwtAccessTokenConverter());
        endpoints.tokenStore(tokenStore());
        endpoints.exceptionTranslator(loggingExceptionTranslator());
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(resourceServerProperties.getJwt().getKeyValue());
        return accessTokenConverter;
    }
    @Bean
    @Primary
    public JdbcClientDetailsService JdbcClientDetailsService(DataSource dataSource) {
    	return new JdbcClientDetailsService(dataSource);
    }
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		
		security.allowFormAuthenticationForClients();
	}
}