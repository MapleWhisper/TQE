package com.tqe.po;

public class Course {
	private String cid;		//�γ̺�
	private Integer cno;	//�����
	private String name;		//�γ���
	private Integer stuNumber;	//ѡ������
	private Integer peroid;		//ѧʱ
	private Integer credit;		//ѧ��
	private String attr;	//�γ�����
	private String  examMode;	//���Է�ʽ	
	private String nature;		//�γ�����
	private String teacherId;	//��ʦId
	private String department;	//����Ժϵ
	private String campus;	//У��
	private String season;	//ѧ�괺/��
	private String combine;	//�ϰ�
	
	private Integer departmentId;
	
	private Teacher teacher;	//������ʦ
	private boolean isEvaled ;	//�Ƿ��Ѿ����۹�
	

	

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

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
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

	public void setIsEvaled(boolean isEvaled) {
		this.isEvaled = isEvaled;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}


	
	
	
	
}
