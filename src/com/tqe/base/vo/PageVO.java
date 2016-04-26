package com.tqe.base.vo;

import java.util.HashMap;

/**
 * Created by Maple on 2015/12/2.
 */
public class PageVO {
    private Integer offset;

    private Integer take;

    private HashMap<String,String> filters = new HashMap<String, String>();

    public PageVO() {

    }

    public PageVO(Integer offset, Integer take) {
        this.offset = offset;
        this.take = take;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTake() {
        return take;
    }

    public void setTake(Integer take) {
        this.take = take;
    }

    public HashMap<String, String> getFilters() {
        return filters;
    }

    public void setFilters(HashMap<String, String> filters) {
        this.filters = filters;
    }

    public PageVO put(String key,String value){
        this.getFilters().put(key,value);
        return this;
    }
}
