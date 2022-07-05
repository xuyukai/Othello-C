package com.xiaoxueqi.blog.Result;

import java.util.Objects;

public class Result {
    private int code;//相应状态码
    private String message;//响应提示信息
    private Object data;//响应结果对象
    public Result(int code){
        this.code=code;
    }

    Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
