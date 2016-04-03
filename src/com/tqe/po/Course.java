package com.tqe.po;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private String teacherId;	//教师Id
	private String department;	//开课院系
	private String campus;	//校区
	private String season;	//学年春/秋
	private String combine;	//合班
	private Integer departmentId;
	
	private Teacher teacher;	//主讲教师
	private boolean isEvaled ;	//是否已经评价过


    /**
     * 课程统计信息
     */
    private Double stuEvalAvgScore=0d;      //学生评教平均分
    private Double teaEvalAvgScore=0d;
    private Double leaEvalAvgScore=0d;

    private List<Double> stuEvalScores=new ArrayList<Double>();    //课程批每批次的得分列表
    private List<Double> teaEvalScores=new ArrayList<Double>();
    private List<Double> leaEvalScores=new ArrayList<Double>();

    private List<Integer> stuEvalLevelCnts=new ArrayList<Integer>(Arrays.asList(0,0,0,0));     //学生评教各等级的统计次数
    private List<Integer> teaEvalLevelCnts=new ArrayList<Integer>(Arrays.asList(0,0,0,0));
    private List<Integer> leaEvalLevelCnts=new ArrayList<Integer>(Arrays.asList(0,0,0,0));

    public Course() {

    }

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

    public boolean isEvaled() {
        return isEvaled;
    }

    public Double getStuEvalAvgScore() {
        return stuEvalAvgScore;
    }

    public void setStuEvalAvgScore(Double stuEvalAvgScore) {
        this.stuEvalAvgScore = stuEvalAvgScore;
    }

    public Double getTeaEvalAvgScore() {
        return teaEvalAvgScore;
    }

    public void setTeaEvalAvgScore(Double teaEvalAvgScore) {
        this.teaEvalAvgScore = teaEvalAvgScore;
    }

    public Double getLeaEvalAvgScore() {
        return leaEvalAvgScore;
    }

    public void setLeaEvalAvgScore(Double leaEvalAvgScore) {
        this.leaEvalAvgScore = leaEvalAvgScore;
    }

    public List<Double> getStuEvalScores() {
        return stuEvalScores;
    }

    public void setStuEvalScores(List<Double> stuEvalScores) {
        this.stuEvalScores = stuEvalScores;
    }

    public List<Double> getTeaEvalScores() {
        return teaEvalScores;
    }

    public void setTeaEvalScores(List<Double> teaEvalScores) {
        this.teaEvalScores = teaEvalScores;
    }

    public List<Double> getLeaEvalScores() {
        return leaEvalScores;
    }

    public void setLeaEvalScores(List<Double> leaEvalScores) {
        this.leaEvalScores = leaEvalScores;
    }

    public List<Integer> getStuEvalLevelCnts() {
        return stuEvalLevelCnts;
    }

    public void setStuEvalLevelCnts(List<Integer> stuEvalLevelCnts) {
        this.stuEvalLevelCnts = stuEvalLevelCnts;
    }

    public List<Integer> getTeaEvalLevelCnts() {
        return teaEvalLevelCnts;
    }

    public void setTeaEvalLevelCnts(List<Integer> teaEvalLevelCnts) {
        this.teaEvalLevelCnts = teaEvalLevelCnts;
    }

    public List<Integer> getLeaEvalLevelCnts() {
        return leaEvalLevelCnts;
    }

    public void setLeaEvalLevelCnts(List<Integer> leaEvalLevelCnts) {
        this.leaEvalLevelCnts = leaEvalLevelCnts;
    }
}
