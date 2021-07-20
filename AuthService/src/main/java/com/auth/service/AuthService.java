/**
 * 
 */
package com.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.common.VacationStatus.VacationUserType;
import com.auth.domain.Member;
import com.auth.repo.MemberRepository;
import com.auth.security.AuthInformation;

/**
 * @author chano
 * @Date : 2021. 5. 13.
 * @Description :
 */
@Service
public class AuthService implements UserDetailsService {

	@Autowired
	MemberRepository memberRepository;
	
	/**
	 * chano
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 * 2021. 5. 14.
	 * description : 유저정보 확인후 권한부여
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		Member member = memberRepository.findBymemberName(username);
		System.out.println("로그인유저 :" + member);
		// TODO Auto-generated method stub+
		 if(member == null) {
	            throw new UsernameNotFoundException("There is no user");
	        }

	   		 AuthInformation loginUser  = new AuthInformation();

	   	   List<GrantedAuthority> Authoritylist = new ArrayList<>();
	   	   String userType = member.getUserType().toString();
	   	   switch(userType) {
	   	       case "ADMIN" :
	   	           // admin
	   	           Authoritylist.add(new SimpleGrantedAuthority("ADMIN"));
	   	       case "USER" :
	   	           // user
	   	           Authoritylist.add(new SimpleGrantedAuthority("USER"));
	   	       break;
	   	   }
	   	
	   	   loginUser.setUsername(member.getPassword());
	   	   loginUser.setPassword(member.getPassword());
	   	   loginUser.setAuthorities(Authoritylist);
	   		System.out.println(loginUser.toString()); 
	   		return loginUser;

	}

  
}