package com.tqe.po;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于保存 评教的结果表
 */
public class ResultTable {

	protected Integer id;	
	protected Integer score;	  //得分
	protected String level;		//等级
	protected String jsonString;	//存储的评价信息


	
	protected Integer eid;	//评教表
	protected String  cid;	//课程号
	protected Integer cno;	//课程序号
	protected Integer bid;	//批次号
	protected String tid;	//教师号
	protected Integer departmentId;
	protected String tname;
	
	


    protected List<String> questionAnsList = new ArrayList<String>();

    protected EvalTable evalTable;  //评教表结果内容
	
	protected Student student;
	protected Batches batches;
	protected Course course;
	protected Teacher teacher;
	protected Leader leader;
	

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Batches getBatches() {
		return batches;
	}
	public void setBatches(Batches batches) {
		this.batches = batches;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public Integer getCno() {
		return cno;
	}
	public void setCno(Integer cno) {
		this.cno = cno;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public Leader getLeader() {
		return leader;
	}
	public void setLeader(Leader leader) {
		this.leader = leader;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}

    public EvalTable getEvalTable() {
        if(this.evalTable==null){
            if(StringUtils.isNotBlank(this.jsonString)){
                this.evalTable = EvalTable.json2Object(this.jsonString);
            }
        }
        return evalTable;
    }

    public void setEvalTable(EvalTable evalTable) {
        this.evalTable = evalTable;
    }

    public List<String> getQuestionAnsList() {
        return questionAnsList;
    }

    public void setQuestionAnsList(List<String> questionAnsList) {
        this.questionAnsList = questionAnsList;
    }
}
