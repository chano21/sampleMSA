package com.first.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.first.domain.Member;
import com.first.repo.MemberRepository;

@RestController(value = "/")
public class FirstController {

	@Autowired
	MemberRepository repo;

	@GetMapping(value = "/members")
	public ResponseEntity<List<Member>> getAllMember() {
		return new ResponseEntity<List<Member>>(repo.findAll(), HttpStatus.OK);
	}
}
