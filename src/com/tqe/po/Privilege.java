package com.tqe.po;

import java.io.Serializable;

public class Privilege implements  Serializable{

	private Integer id;
	private String url;
	private String name;
	private Integer stu;
	private Integer tea;
	private Integer adm;
	private Integer lea;
	private Integer editable;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStu() {
		return stu;
	}
	public void setStu(Integer stu) {
		this.stu = stu;
	}
	public Integer getTea() {
		return tea;
	}
	public void setTea(Integer tea) {
		this.tea = tea;
	}
	public Integer getAdm() {
		return adm;
	}
	public void setAdm(Integer adm) {
		this.adm = adm;
	}
	public Integer getLea() {
		return lea;
	}
	public void setLea(Integer lea) {
		this.lea = lea;
	}

	public Integer getEditable() {
		return editable;
	}

	public void setEditable(Integer editable) {
		this.editable = editable;
	}
}
