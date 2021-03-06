package com.second.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author chano
 * @Date : 2021. 5. 17.
 * @Description :
 */
@Configuration
@EnableResourceServer
class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	  http.authorizeRequests()
          .antMatchers(HttpMethod.GET,"/second").authenticated()
    	 // http.authorizeRequests()
          .antMatchers("/second").authenticated()
          .antMatchers(HttpMethod.POST,"/second").permitAll();
//    	http
//        .httpBasic()
//            .and()
//        .authorizeRequests()
//            .antMatchers("/member/**").permitAll()
//            .and()           
//        .authorizeRequests()
//            .antMatchers("/vacation/**").hasAnyRole("USER")
//            .anyRequest().authenticated()
//            .and().formLogin().disable();
    	
//    	http
//        .httpBasic()
//            .and()
//        .authorizeRequests()
//            .antMatchers("/member/**").permitAll()
//            .and()
//        .authorizeRequests()
//            .antMatchers("/vacation/**").hasAnyRole("USER")
//            .anyRequest().authenticated();
//            .and()
//        .formLogin()
//            .permitAll();
//    	
//    	http.httpBasic().and()
//    	
//    	   .antMatcher("/vacation/**")
//    	      .antMatchers("/**").hasRole("USER")
//    	      .and()
//    	   .antMatcher("/member/**")
//    	   .authorizeRequests()
//    	      .antMatchers("/new").permitAll()
//    	      .antMatchers("/all").hasRole("USER");
    }
}