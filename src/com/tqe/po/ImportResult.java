package com.tqe.po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maple on 2016/4/3.
 */
public class ImportResult {

    private String message;       //导入信息

    private String importType;  //导入类型

    private int size = 0;

    private int successCnt = 0;     //导入成功个数

    private int failCnt = 0;        //导入失败个数

    private List<String> failMegs = new ArrayList<String>();  //导入失败的错误休信息

    private int existCnt = 0;       //数据库已经存在个数

    public ImportResult(int size,String importType) {
        this.size = size;
        this.importType = importType;
    }
    public  ImportResult(){

    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public int getSuccessCnt() {
        return successCnt;
    }

    public void setSuccessCnt(int successCnt) {
        this.successCnt = successCnt;
    }

    public int getFailCnt() {
        return failCnt;
    }

    public void setFailCnt(int failCnt) {
        this.failCnt = failCnt;
    }

    public int getExistCnt() {
        return existCnt;
    }

    public void setExistCnt(int existCnt) {
        this.existCnt = existCnt;
    }

    public List<String> getFailMegs() {
        return failMegs;
    }

    public void setFailMegs(List<String> failMegs) {
        this.failMegs = failMegs;
    }

    public void addSuccessCnt(){
        this.successCnt++;
    }

    public void addFailCnt(){
        this.failCnt++;
    }

    public void addExitCnt(){
        this.existCnt++;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
