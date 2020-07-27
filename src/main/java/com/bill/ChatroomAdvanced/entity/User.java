package com.bill.ChatroomAdvanced.entity;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class User {

	private String userAccount;
	private String userPassword;
	private String userName;
	
	private Integer dmType;
//	private Set<Friends> friend;
	
}
