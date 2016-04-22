package com.tqe.base.enums;

import java.util.Date;

/**
 * Created by Maple on 2016/3/16.
 */
public enum EvalTableType {
    STU("学生评教师"),
    TEA("教师评教师"),
    TEASTU("教师评学生"),
    LEA("领导评学生");


    private String name;
    EvalTableType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public  static EvalTableType toEvalTableType(String evalTableType){
        if(evalTableType.equalsIgnoreCase("STU")){
            return EvalTableType.STU;
        }
        if(evalTableType.equalsIgnoreCase("TEA")){
            return EvalTableType.TEA;
        }
        if(evalTableType.equalsIgnoreCase("TEASTU")){
            return EvalTableType.TEASTU;
        }
        if(evalTableType.equalsIgnoreCase("LEA")){
            return EvalTableType.LEA;
        }
        throw new IllegalArgumentException("未知的评教表类型");
    }
}
