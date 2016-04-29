package com.tqe.vo;

import com.tqe.po.BatchScore;
import com.tqe.po.Teacher;

import java.util.List;

/**
 * Created by Maple on 2016/4/28.
 */
public class TeacherVO  {


    private Teacher teacher;

    private List<BatchScore> batchScoreList;




    public  TeacherVO(){

    }

    public TeacherVO(Teacher teacher) {
        this.teacher = teacher;
    }


    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<BatchScore> getBatchScoreList() {
        return batchScoreList;
    }

    public void setBatchScoreList(List<BatchScore> batchScoreList) {
        this.batchScoreList = batchScoreList;
    }
}
