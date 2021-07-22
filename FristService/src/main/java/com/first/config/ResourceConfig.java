package com.first.config;

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
          .antMatchers(HttpMethod.GET,"/first/members").hasAnyRole("USER").and()//.authenticated()
    	  .authorizeRequests().antMatchers("/second").authenticated().anyRequest().authenticated();
         // .antMatchers(HttpMethod.POST,"/first").permitAll();

    	//    	http
//        .requestMatchers()
//            .antMatchers("/first/**")
//            .and()
//        .authorizeRequests()
//            .antMatchers("/first/resource").authenticated()
//            .antMatchers("/first/members").access( "#oauth2.hasScope('read')");
        
//  	  http.authorizeRequests()
//      .antMatchers(HttpMethod.GET,"/first/members").access("#oauth2.hasScope('read')").and()//.authenticated()
//	  .authorizeRequests().antMatchers("/second").authenticated().anyRequest().authenticated();
     // .antMatchers(HttpMethod.POST,"/first").permitAll();

    	  
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