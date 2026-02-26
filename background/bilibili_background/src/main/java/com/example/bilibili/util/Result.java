package com.example.bilibili.util;

public class Result {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Object data;
    public Result successResult(String msg,Object data){
        this.code=1;
        this.msg=msg;
        this.data=data;
        return this;
    }
    public Result successResult(String msg){
        this.code=1;
        this.msg=msg;
        this.data=null;
        return this;
    }
    public Result errorResult(String msg,Object data){
        this.code=0;
        this.msg=msg;
        this.data=data;
        return this;
    }
    public Result errorResult(String msg){
        this.code=0;
        this.msg=msg;
        this.data=null;
        return this;
    }

}
