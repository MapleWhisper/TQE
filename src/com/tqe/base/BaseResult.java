package com.tqe.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * Created by Maple on 2015/11/28.
 */
public class BaseResult implements Serializable{

    private boolean success ;
    private String message;
    private Object item;

    /**
     * 创建一个成功的结果
     */
    public static BaseResult createSuccess(String message){
        return new BaseResult(true,message);
    }

    public static BaseResult createSuccess(Object item){
        BaseResult result = new BaseResult(true,"ok");
        result.setItem(item);
        return result;

    }

    /**
     * 创建一个失败的结果
     */
    public static BaseResult createFailure(String message){

        return new BaseResult(false,message);
    }

    public BaseResult() {
    }

    public BaseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}
