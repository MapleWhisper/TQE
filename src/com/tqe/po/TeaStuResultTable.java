package com.tqe.po;

import java.io.Serializable;

public class TeaStuResultTable extends ResultTable implements Serializable{
	
	private String sid;	//学生号
	
	private String sname;



	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	
	
}
