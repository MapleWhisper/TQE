package com.tqe.po;

import java.io.Serializable;

public class StuResultTable extends ResultTable implements Serializable{
	private String  sid;	//学生id

	
	private String tname;
	private String sname;
	private String tid;

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	
	
}
