package com.zyc.oneselfapp.exception;

/**
 * Created by zyc on 2016/12/7.
 */

public class ApiException extends RuntimeException {
    private String code;
    private String msg;
    static final long serialVersionUID = 1l;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ApiException(String code, String msg) {
        this.code = code;
        this.msg = msg;

    }
}
