package com.tqe.base.enums;

/**
 * Created by Maple on 2016/3/27.
 */
public enum EvalResultLevel {
    BEST("优秀"),
    GOOD("良好"),
    AVERAGE("一般"),
    BAD("差");

    private String name;

    EvalResultLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
