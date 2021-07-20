package com.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>  {
	Member findBymemberName(String name);
}