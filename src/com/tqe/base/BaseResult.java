package com.tqe.base;

import java.io.Serializable;

/**
 * Created by Maple on 2015/11/28.
 */
public class BaseResult implements Serializable{

    private boolean success ;
    private String message;

    /**
     * 返回正确的结果相应
     * @param message 成功消息
     */
    public static BaseResult createSuccess(String message){
        return new BaseResult(true,message);
    }

    /**
     * 返回失败的结果相应
     * @param message 失败消息
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
}
