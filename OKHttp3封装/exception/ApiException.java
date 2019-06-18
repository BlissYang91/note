package com.app.ytf.httpdemo.exception;

/**
 * @author ytf
 *  api接口请求错误/异常统一处理类
 */
public class ApiException extends Exception{
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String msg;

    public ApiException( Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
}
