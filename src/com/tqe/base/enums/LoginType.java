package com.tqe.base.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Maple on 2016/6/3.
 */
public enum LoginType {

    SCHOOL("学校登陆"),
    INNER("内置登陆");

    private String name;
    LoginType(String name){
        this.name = name;
    }

    public static LoginType toLoginType(String loginType){
        if(StringUtils.isNotBlank(loginType)){
            return LoginType.valueOf(loginType.toUpperCase());
        }else {
            return null;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
