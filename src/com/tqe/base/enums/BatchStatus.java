package com.tqe.base.enums;

import java.util.Date;

/**
 * Created by Maple on 2016/3/16.
 */
public enum BatchStatus {
    PENDING("未开始"),
    FINISH("已结束"),
    RUNNING("进行中"),
    UNKNOWN("未知");


    private String name;
    BatchStatus(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getBatchStatusName(Date start,Date end){

        if(start==null || end ==null){
            return UNKNOWN.getName();
        }

        long nowTime = new Date().getTime();
        if(nowTime < start.getTime()){
            return PENDING.getName();
        }else if(nowTime > start.getTime() && nowTime < end.getTime()){
            return RUNNING.getName();
        }else{
            return FINISH.getName();
        }
    }
}
