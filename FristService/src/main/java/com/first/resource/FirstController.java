package com.first.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.first.domain.Member;
import com.first.repo.MemberRepository;

@RestController(value = "/")
public class FirstController {

	@Autowired
	MemberRepository repo;

	//@PreAuthorize("#oauth2.hasScope('test.scope')")
	@PreAuthorize("#oauth2.hasScope('test.scope')")
	@GetMapping(value = "/members")
	public ResponseEntity<List<Member>> getAllMember() {
		//System.out.println("어스 " + auth.toString());
		return new ResponseEntity<List<Member>>(repo.findAll(), HttpStatus.OK);

	}
}
