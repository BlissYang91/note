package com.thesis.course.minicourse.api.exception;

/**
 * @autor YangTianFu
 * @Date 2019/3/21  18:04
 * api接口错误
 */
public class ApiException extends  Exception{
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String msg;

    public int getCode() {
        return code;
    }

    public ApiException(Throwable cause,int code) {
        super(cause);
        this.code = code;
    }

    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
