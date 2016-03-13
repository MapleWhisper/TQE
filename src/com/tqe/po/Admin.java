package com.tqe.po;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tqe.utils.MD5Utils;

@Component
public class Admin implements Serializable{
	
	private Integer id;
	private String name;					//����
	private String username;				//�û���	���Ȳ��ܳ���20
	private String password;				//����	���Ȳ��ܳ���20
	private String position;				//ְλ	���Ȳ��ܳ���30
	
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
	private Set<Privilege> privileges;		//����Աӵ�е�Ȩ��
	
	private Integer[] privilegeIds;			//����Ա��Ȩ��ids
	*/
}