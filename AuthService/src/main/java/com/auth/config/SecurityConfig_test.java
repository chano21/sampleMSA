/**
 * 
 */
package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth.service.AuthService;

/**
 * @author chano
 * @Date : 2021. 5. 16.
 * @Description : 로그인 시큐리티 설정
 */

//@Configuration
//@EnableWebSecurity
public class SecurityConfig_test extends WebSecurityConfigurerAdapter  {    

    @Autowired
    private AuthService AuthService;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        //.antMatchers("/h2-console/**").permitAll()
        .antMatchers("/**").permitAll()
        .and().csrf().disable()
        .headers().disable();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder builder)
    throws Exception {
        builder.authenticationProvider(authenticationProvider());
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth)
//    throws Exception {
//    	auth.authenticationProvider(authenticationProvider());
//    }

    

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(AuthService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    

}
