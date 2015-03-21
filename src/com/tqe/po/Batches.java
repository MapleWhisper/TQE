package com.tqe.po;

public class Batches {
	private Integer id;
	private String name;
	private Integer courseNumber;
	private Integer curCourseNumber;
	private String season;
	
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
	
	
}
