package com.tqe.po;

import java.io.Serializable;

public class TeaResultTable extends ResultTable implements Serializable{
	private String  tid;	//教师号
	
	private String tname;
	
	
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	
	
	
	
}
