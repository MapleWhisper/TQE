package com.tqe.base.exception;

/**
 * Created by Maple on 2016/6/4.
 */
public class UserNotExistException extends RuntimeException{

    public UserNotExistException()  {}
    public UserNotExistException(String message) {
        super(message);
    }
}
