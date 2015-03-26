package com.tqe.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Batches {
	private Integer id;
	private String name;
	private Integer courseNumber;
	private Integer curCourseNumber;
	private String season;
	
	private Date beginDate;		//评教开始日期
	private Date endDate;		//评教结束日期
	private EvalTable defaultEval;	//默认的评教指标
	private int evalTableId;
	
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
	public Integer getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(Integer courseNumber) {
		this.courseNumber = courseNumber;
	}
	public Integer getCurCourseNumber() {
		return curCourseNumber;
	}
	public void setCurCourseNumber(Integer curCourseNumber) {
		this.curCourseNumber = curCourseNumber;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public EvalTable getDefaultEval() {
		return defaultEval;
	}
	public void setDefaultEval(EvalTable defaultEval) {
		this.defaultEval = defaultEval;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getEvalTableId() {
		return evalTableId;
	}
	public void setEvalTableId(int evalTableId) {
		this.evalTableId = evalTableId;
	}
	
	
}
