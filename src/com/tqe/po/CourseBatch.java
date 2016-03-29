package com.tqe.po;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Maple on 2016/3/24.
 */
public class CourseBatch {
    DecimalFormat df = new DecimalFormat("#.00");

    private String cid;
    private Integer cno;
    private Integer bid;

    private Double stuEvalAvgScore=0d;    //学生评教平均分
    private Double teaEvalAvgScore=0d;
    private Double leaEvalAvgScore=0d;

    private List<Integer> stuEvalLevelCnts; //学生评教各等级的个数
    private List<Integer> teaEvalLevelCnts;
    private List<Integer> leaEvalLevelCnts;

    private String stuEvalTableJsonString;
    private String teaEvalTableJsonString;
    private String leaEvalTableJsonString;

    private EvalTable stuEvalTable;
    private EvalTable teaEvalTable;
    private EvalTable leaEvalTable;

    private Integer stuEvalTotal;   //总学生人数
    private Integer stuEvalCnt;     //已经评教的学生人数



    private Course course;
    private Batches batch;

    public CourseBatch() {
    }

    public CourseBatch(String cid, Integer cno, Integer bid) {
        this.cid = cid;
        this.cno = cno;
        this.bid = bid;
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

    public Double getStuEvalAvgScore() {
        return stuEvalAvgScore;
    }

    public void setStuEvalAvgScore(Double stuEvalAvgScore) {
        this.stuEvalAvgScore = stuEvalAvgScore;
    }

    public Double getTeaEvalAvgScore() {
        return Double.parseDouble(df.format(teaEvalAvgScore));
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

    public Integer getStuEvalTotal() {
        return stuEvalTotal;
    }

    public void setStuEvalTotal(Integer stuEvalTotal) {
        this.stuEvalTotal = stuEvalTotal;
    }

    public Integer getStuEvalCnt() {
        return stuEvalCnt;
    }

    public void setStuEvalCnt(Integer stuEvalCnt) {
        this.stuEvalCnt = stuEvalCnt;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Batches getBatch() {
        return batch;
    }

    public void setBatch(Batches batch) {
        this.batch = batch;
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

    public String getStuEvalTableJsonString() {
        return stuEvalTableJsonString;
    }

    public void setStuEvalTableJsonString(String stuEvalTableJsonString) {
        this.stuEvalTableJsonString = stuEvalTableJsonString;
    }

    public String getTeaEvalTableJsonString() {
        return teaEvalTableJsonString;
    }

    public void setTeaEvalTableJsonString(String teaEvalTableJsonString) {
        this.teaEvalTableJsonString = teaEvalTableJsonString;
    }

    public String getLeaEvalTableJsonString() {
        return leaEvalTableJsonString;
    }

    public void setLeaEvalTableJsonString(String leaEvalTableJsonString) {
        this.leaEvalTableJsonString = leaEvalTableJsonString;
    }

    public EvalTable getStuEvalTable() {
        return stuEvalTable;
    }

    public void setStuEvalTable(EvalTable stuEvalTable) {
        this.stuEvalTable = stuEvalTable;
    }

    public EvalTable getTeaEvalTable() {
        return teaEvalTable;
    }

    public void setTeaEvalTable(EvalTable teaEvalTable) {
        this.teaEvalTable = teaEvalTable;
    }

    public EvalTable getLeaEvalTable() {
        return leaEvalTable;
    }

    public void setLeaEvalTable(EvalTable leaEvalTable) {
        this.leaEvalTable = leaEvalTable;
    }

    @Override
    public String toString() {
        return "CourseBatch{" +
                "cid='" + cid + '\'' +
                ", cno=" + cno +
                ", bid=" + bid +
                ", stuEvalAvgScore=" + stuEvalAvgScore +
                ", teaEvalAvgScore=" + teaEvalAvgScore +
                ", leaEvalAvgScore=" + leaEvalAvgScore +
                ", stuEvalLevelCnts=" + stuEvalLevelCnts +
                ", teaEvalLevelCnts=" + teaEvalLevelCnts +
                ", leaEvalLevelCnts=" + leaEvalLevelCnts +
                ", stuEvalTotal=" + stuEvalTotal +
                ", stuEvalCnt=" + stuEvalCnt +
                ", course=" + course +
                ", batch=" + batch +
                '}';
    }
}
