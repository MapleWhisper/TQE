package com.tqe.po;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Department implements Serializable{
	private Integer id;
	private String name;

	public Department(){

	}
	public Department(String name) {
		this.name = name;

	}

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
	
}
