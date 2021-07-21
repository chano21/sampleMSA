package com.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.auth.service.AuthService;

/**
 * @author chano
 * @Date : 2021. 5. 16.
 * @Description : oauth2.0 인가설정
 */

@Configuration
@EnableAuthorizationServer
public class AuthConfiguration extends AuthorizationServerConfigurerAdapter {
//	@Value("${spring.redis.ip}")
//	private String redis_ip;
//
//	@Value("${spring.redis.port}")
//	private int redis_port;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AuthService authService;
	
	@Autowired
	RedisConnectionFactory redisConnectionFactory;

	 @Autowired
	 private ResourceServerProperties resourceServerProperties;

//	    @Bean
//	    public static PasswordEncoder passwordEncoder() {
//	    	DelegatingPasswordEncoder delPasswordEncoder=  (DelegatingPasswordEncoder)PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	        BCryptPasswordEncoder bcryptPasswordEncoder =new BCryptPasswordEncoder();
//		    delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
//		    return delPasswordEncoder;      
//	    }
//	    
	 

	@Value("${security.oauth2.client.client-id}")
	private String client_id;

	@Value("${security.oauth2.client.client-secret}")
	private String client_secret;

//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory().withClient(client_id).secret(client_secret).scopes("read,write").secret(passwordEncoder().encode("SECRET"))
//				.authorizedGrantTypes("password");
//	}

//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory)).authenticationManager(authenticationManager)
//				.userDetailsService(authService).accessTokenConverter(jwtAccessTokenConverter());
//	}
//    @Bean
//    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
    throws Exception {
        super.configure(endpoints);
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
        .authenticationManager(authenticationManager);
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
       
        System.out.println("나아라 : " + resourceServerProperties.getClientId());
        System.out.println("나아라 : " + resourceServerProperties.getClientSecret());
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