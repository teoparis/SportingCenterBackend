package com.thoughtmechanix.zuulsvr.model;


import java.util.List;

public class UserInfo {
	private String id, displayName, email;
	private List<String> roles;

	public UserInfo(String toString, String displayName, String email, List<String> roles) {
		id = toString;
		this.displayName = displayName;
		this.email = email;
		this.roles = roles;
	}

	public String getUserId() {
		return id;
	}
}
