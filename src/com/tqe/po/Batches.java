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
	
	private EvalTable stuEval;	//默认的学生评教指标表
	private Integer stuEvalId;		//默认的学生评教指标表Id
	
	private EvalTable teaEval;	//默认的教师评教指标表
	private Integer teaEvalId;		//默认的教师评教指标表Id
	
	private EvalTable leadEval;	//默认的领导评教指标表
	private Integer leadEvalId;		//默认的领导评教指标表Id
	
	private EvalTable teaStuEval;	//默认的教师评价学生表
	private Integer teaStuEvalId;	//默认的教师评价学生表Id
	
	
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
	
	public EvalTable getStuEval() {
		return stuEval;
	}
	public void setStuEval(EvalTable stuEval) {
		this.stuEval = stuEval;
	}
	public Integer getStuEvalId() {
		return stuEvalId;
	}
	public void setStuEvalId(Integer stuEvalId) {
		this.stuEvalId = stuEvalId;
	}
	public EvalTable getTeaEval() {
		return teaEval;
	}
	public void setTeaEval(EvalTable teaEval) {
		this.teaEval = teaEval;
	}
	public Integer getTeaEvalId() {
		return teaEvalId;
	}
	public void setTeaEvalId(Integer teaEvalId) {
		this.teaEvalId = teaEvalId;
	}
	public EvalTable getLeadEval() {
		return leadEval;
	}
	public void setLeadTval(EvalTable leadEval) {
		this.leadEval = leadEval;
	}
	public Integer getLeadEvalId() {
		return leadEvalId;
	}
	public void setLeadEvalId(Integer leadEvalId) {
		this.leadEvalId = leadEvalId;
	}
	public EvalTable getTeaStuEval() {
		return teaStuEval;
	}
	public void setTeaStuEval(EvalTable teaStuEval) {
		this.teaStuEval = teaStuEval;
	}
	public Integer getTeaStuEvalId() {
		return teaStuEvalId;
	}
	public void setTeaStuEvalId(Integer teaStuEvalId) {
		this.teaStuEvalId = teaStuEvalId;
	}
	public void setLeadEval(EvalTable leadEval) {
		this.leadEval = leadEval;
	}
	
	
	
}
