package com.tqe.base.enums;

/**
 * Created by Maple on 2016/4/3.
 */
public enum ImportType {

    TEACHER("教师"),
    STUDENT("学生"),
    COURSE("课程"),
    SC("学生选课信息");

    private String name;
    ImportType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
