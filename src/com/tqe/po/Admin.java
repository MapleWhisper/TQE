package com.tqe.po;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tqe.utils.MD5Utils;

@Component
public class Admin implements Serializable{
	
	private Integer id;
	private String name;					//姓名
	private String username;				//用户名	长度不能超过20
	private String password;				//密码	长度不能超过20
	private String position;				//职位	长度不能超过30
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
	
	/*
	private Set<Privilege> privileges;		//管理员拥有的权限
	
	private Integer[] privilegeIds;			//管理员的权限ids
	*/
}