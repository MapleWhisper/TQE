package com.tqe.vo;

import com.tqe.po.BatchScore;
import com.tqe.po.Student;
import com.tqe.po.Teacher;

import java.util.List;

/**
 * Created by Maple on 2016/4/28.
 */
public class StudentVO {


    private Student student;

    private List<BatchScore> batchScoreList;




    public StudentVO(){

    }

    public StudentVO(Student student) {
        this.student = student;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<BatchScore> getBatchScoreList() {
        return batchScoreList;
    }

    public void setBatchScoreList(List<BatchScore> batchScoreList) {
        this.batchScoreList = batchScoreList;
    }
}
