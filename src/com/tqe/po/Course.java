package com.tqe.po;

public class Course {
	private String cid;		//课程号
	private Integer cno;	//课序号
	private String name;		//课程名
	private Integer stuNumber;	//选课人数
	private Integer peroid;		//学时
	private Integer credit;		//学分
	private String attr;	//课程属性
	private String  examMode;	//考试方式	
	private String nature;		//课程性质
	private Integer teacherId;	//教师Id
	private String department;	//开课院系
	private String campus;	//校区
	private String season;	//学年春/秋
	private String combine;	//合班
	
	private Teacher teacher;	//主讲教师
	private boolean isEvaled ;	//是否已经评价过
	

	

	public Integer getCno() {
		return cno;
	}

	public void setCno(Integer cno) {
		this.cno = cno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStuNumber() {
		return stuNumber;
	}

	public void setStuNumber(Integer stuNumber) {
		this.stuNumber = stuNumber;
	}

	public Integer getPeroid() {
		return peroid;
	}

	public void setPeroid(Integer peroid) {
		this.peroid = peroid;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getExamMode() {
		return examMode;
	}

	public void setExamMode(String examMode) {
		this.examMode = examMode;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getCombine() {
		return combine;
	}

	public void setCombine(String combine) {
		this.combine = combine;
	}

	public boolean getIsEvaled() {
		return isEvaled;
	}

	public void setEvaled(boolean isEvaled) {
		this.isEvaled = isEvaled;
	}


	
	
	
	
}
