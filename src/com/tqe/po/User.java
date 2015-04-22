package com.tqe.po;

import org.springframework.util.StringUtils;

import com.tqe.utils.MD5Utils;

public class User {
	private String id;
	private String username;
	private String password;
	private String type;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
		if(StringUtils.hasText(password) && password.length()!=32){
			this.password = MD5Utils.string2MD5(password);
		}
		
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
