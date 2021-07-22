package com.second.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.second.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>  {
	Member findBymemberName(String name);
}