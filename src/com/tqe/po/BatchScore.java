package com.tqe.po;

import java.util.Date;

/**
 * Created by Maple on 2016/4/28.
 */
public class BatchScore {

    private String id;      //教师Id 或者学生 id
    private Integer bid;    //批次号
    private String batchName;   //批次名

    private Double stuAvgScore;
    private Double teaAvgScore;
    private Double leaAvgScore;

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
}
