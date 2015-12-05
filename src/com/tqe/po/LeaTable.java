package com.tqe.po;

import java.io.Serializable;

public class LeaTable extends Table implements Serializable{
	private Integer id;	
	private Integer  lid;	//领导号
	
	private String sname;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLid() {
		return lid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}


	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
}
