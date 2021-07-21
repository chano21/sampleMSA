package com.first.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.first.common.MemberStatus.MemberUserType;

import lombok.Data;

@Entity
@Table(name="Member")
@Data
public class Member {
  
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "memberName", length = 50, nullable = false)
    private String memberName; 
    
    @Column(name = "password", length = 100, nullable = false)
    private String password; 
    
    @Enumerated(EnumType.STRING)
    @Column(name = "userType", nullable = false)
    private MemberUserType userType; 
    
    
}