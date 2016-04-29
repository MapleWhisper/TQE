package com.tqe.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tqe.utils.MD5Utils;

@Component
public class Teacher implements Serializable{
	
	private String id;
	private String password;
	private String name;
	private String sex;
	private Date birthday;
	private String phone;
	private String email;
	private String addr;
	private String nation;
	private String postId;
	private String idType;
	private String idNumber;
	private String politicalStatus;
	private String department;
	private String title;
	private String folk;
	private Date workDate;
	
	private Integer departmentId;
	
	private Double stuAvgScore;
    private Double teaAvgScore;
    private Double leaAvgScore;

    private Date mtime;

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public Double getStuAvgScore() {
        return stuAvgScore;
    }

    public void setStuAvgScore(Double stuAvgScore) {
        this.stuAvgScore = stuAvgScore;
    }

    public Double getTeaAvgScore() {
        return teaAvgScore;
    }

    public void setTeaAvgScore(Double teaAvgScore) {
        this.teaAvgScore = teaAvgScore;
    }

    public Double getLeaAvgScore() {
        return leaAvgScore;
    }

    public void setLeaAvgScore(Double leaAvgScore) {
        this.leaAvgScore = leaAvgScore;
    }


	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPoliticalStatus() {
		return politicalStatus;
	}
	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFolk() {
		return folk;
	}
	public void setFolk(String folk) {
		this.folk = folk;
	}
	public Date getWorkDate() {
		return workDate;
	}
	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}


    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", addr='" + addr + '\'' +
                ", nation='" + nation + '\'' +
                ", postId='" + postId + '\'' +
                ", idType='" + idType + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", politicalStatus='" + politicalStatus + '\'' +
                ", department='" + department + '\'' +
                ", title='" + title + '\'' +
                ", folk='" + folk + '\'' +
                ", workDate=" + workDate +
                ", departmentId=" + departmentId +
                '}';
    }
}
