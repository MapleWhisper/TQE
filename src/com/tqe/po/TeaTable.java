package com.tqe.po;

import java.io.Serializable;

public class TeaTable extends Table implements Serializable{
	private String  tid;	//教师号
	
	private Integer departmentId;
	private String tname;
	
	
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	
	
	
	
}
