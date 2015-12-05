package com.tqe.base;

import java.io.Serializable;

/**
 * Created by Maple on 2015/11/28.
 */
public class BaseResult implements Serializable{

    private boolean success ;
    private String message;

    /**
     * ������ȷ�Ľ����Ӧ
     * @param message �ɹ���Ϣ
     */
    public static BaseResult createSuccess(String message){
        return new BaseResult(true,message);
    }

    /**
     * ����ʧ�ܵĽ����Ӧ
     * @param message ʧ����Ϣ
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
