package com.sportingCenterBackEnd.dto;

import java.util.List;

import lombok.Value;

@Value
public class UserInfo {
	private String id, displayName, email;
	private List<String> roles;

	public UserInfo(String toString, String displayName, String email, List<String> roles) {
		id = toString;
		this.displayName = displayName;
		this.email = email;
		this.roles = roles;
	}

	public String toString() {
		return displayName + " " + email;
	}

	public List<String> getRoles() {
		return this.roles;
	}

}
