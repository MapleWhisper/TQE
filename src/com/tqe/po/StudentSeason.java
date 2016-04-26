package com.tqe.po;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maple on 2016/4/20.
 * 学生每学期的统计信息
 */
public class StudentSeason {

    private String sid;     //学生号
    private String season ; //学期

    private Double avgScore;        //平均分

    private List<Double> avgScoreList;        //每个批次的平均得分

    private List<Integer> levelCnts;    //等级统计

    private Integer resultTableNum;     //当前统计的评教表的数据

    private String resultTableJsonString;   //评教表项内容统计

    private Date mtime; //最后修改时间

    private List<Pair<String,List<String>>> questionList;   //评教的回答标题和 答案列表

    private String questionListString;


    public StudentSeason(){

    }

    public StudentSeason(String sid, String season) {
        this.sid = sid;
        this.season = season;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public List<Double> getAvgScoreList() {
        return avgScoreList;
    }

    public void setAvgScoreList(List<Double> avgScoreList) {
        this.avgScoreList = avgScoreList;
    }

    public List<Integer> getLevelCnts() {
        return levelCnts;
    }

    public void setLevelCnts(List<Integer> levelCnts) {
        this.levelCnts = levelCnts;
    }

    public Integer getResultTableNum() {
        return resultTableNum;
    }

    public void setResultTableNum(Integer resultTableNum) {
        this.resultTableNum = resultTableNum;
    }

    public String getResultTableJsonString() {
        return resultTableJsonString;
    }

    public void setResultTableJsonString(String resultTableJsonString) {
        this.resultTableJsonString = resultTableJsonString;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public List<Pair<String, List<String>>> getQuestionList() {
        if(this.questionList==null && StringUtils.isNotBlank(this.questionListString)){
            List<Pair> list = JSON.parseArray(this.questionListString, Pair.class);
            questionList  = new ArrayList<Pair<String, List<String>>>();
            for(Pair pair : list){
                questionList.add(pair);
            }
        }
        return questionList;
    }

    public void setQuestionList(List<Pair<String, List<String>>> questionList) {

        this.questionList = questionList;
    }


    public String getQuestionListString() {
        return questionListString;
    }

    public void setQuestionListString(String questionListString) {
        this.questionListString = questionListString;
    }
}
