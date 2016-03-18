package com.tqe.base.enums;

/**
 * Created by Maple on 2016/3/16.
 */
public enum UserType {
    ADMIN("admin"),
    STUDENT("student"),
    TEACHER("teacher"),
    LEADER("leader");

    private String name;
    UserType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  static UserType toUserType(String userType){
        if(userType.equals("admin")){
            return UserType.ADMIN;
        }
        if(userType.equals("student")){
            return UserType.STUDENT;
        }
        if(userType.equals("teacher")){
            return UserType.TEACHER;
        }
        if(userType.equals("leader")){
            return UserType.LEADER;
        }
        throw new IllegalArgumentException("未知的用户类型");
    }
}
