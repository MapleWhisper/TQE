package com.tqe.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.tqe.utils.MD5Utils;

public class Student implements Serializable{
	private String sid;
	private String name;
	private String password;
	private String sex;
	private Date birthday;
	private String idNumber;
	private String nation;
	private String politicalStatus;
	private String language;
	private String department;
	private String major;
	private String clazz;
	private String field;
	private String educationBackground;
	private String grade;
	private String hasRoll;
	private String atSchool;
	private String style;
	private String campus;
	
	private Integer departmentId;
	private Integer classId;
	private Integer majorId;

	private boolean isEvaled;	//�ж�ѧ���Ƿ��Ѿ�����
	
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getBirthday(){
		return this.birthday;
	}

	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getEducationBackground() {
		return educationBackground;
	}
	public void setEducationBackground(String educationBackground) {
		this.educationBackground = educationBackground;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getHasRoll() {
		return hasRoll;
	}
	public void setHasRoll(String hasRoll) {
		this.hasRoll = hasRoll;
	}
	public String getAtSchool() {
		return atSchool;
	}
	public void setAtSchool(String atSchool) {
		this.atSchool = atSchool;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getPoliticalStatus() {
		return politicalStatus;
	}
	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public Integer getMajorId() {
		return majorId;
	}
	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}
	public boolean getIsEvaled() {
		return isEvaled;
	}
	public void setIsEvaled(boolean isEvaled) {
		this.isEvaled = isEvaled;
	}


	@Override
	public String toString() {
		return "Student{" +
				"sid='" + sid + '\'' +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", sex='" + sex + '\'' +
				", birthday=" + birthday +
				", idNumber='" + idNumber + '\'' +
				", nation='" + nation + '\'' +
				", politicalStatus='" + politicalStatus + '\'' +
				", language='" + language + '\'' +
				", department='" + department + '\'' +
				", major='" + major + '\'' +
				", clazz='" + clazz + '\'' +
				", field='" + field + '\'' +
				", educationBackground='" + educationBackground + '\'' +
				", grade='" + grade + '\'' +
				", hasRoll='" + hasRoll + '\'' +
				", atSchool='" + atSchool + '\'' +
				", style='" + style + '\'' +
				", campus='" + campus + '\'' +
				", departmentId=" + departmentId +
				", classId=" + classId +
				", majorId=" + majorId +
				", isEvaled=" + isEvaled +
				'}';
	}


}
