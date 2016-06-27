package com.tqe.po;

import java.util.Date;

/**
 * Created by Maple on 2016/4/28.
 * 每一个批次的成绩统计
 * 包含学生对应每个批次的成绩和 教师对应的每个批次的成绩
 */
public class BatchScore {

    private String id;      //教师Id 或者学生 id
    private Integer bid;    //批次号
    private String batchName;   //批次名

    private Double stuAvgScore;
    private Double teaAvgScore;
    private Double leaAvgScore;
    private Double teaStuAvgScore;

    private Double stuAvg;          //全局平均成绩
    private Double teaAvg;
    private Double leaAvg;
    private Double teaStuAvg;

    private Date mtime;




    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
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

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public Double getStuAvg() {
        return stuAvg;
    }

    public void setStuAvg(Double stuAvg) {
        this.stuAvg = stuAvg;
    }

    public Double getTeaAvg() {
        return teaAvg;
    }

    public void setTeaAvg(Double teaAvg) {
        this.teaAvg = teaAvg;
    }

    public Double getLeaAvg() {
        return leaAvg;
    }

    public void setLeaAvg(Double leaAvg) {
        this.leaAvg = leaAvg;
    }

    public Double getTeaStuAvgScore() {
        return teaStuAvgScore;
    }

    public void setTeaStuAvgScore(Double teaStuAvgScore) {
        this.teaStuAvgScore = teaStuAvgScore;
    }

    public Double getTeaStuAvg() {
        return teaStuAvg;
    }

    public void setTeaStuAvg(Double teaStuAvg) {
        this.teaStuAvg = teaStuAvg;
    }
}
