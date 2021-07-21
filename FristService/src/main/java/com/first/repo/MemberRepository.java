package com.first.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>  {
	Member findBymemberName(String name);
}