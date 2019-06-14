package com.thesis.course.minicourse.api.exception;

/**
 * @autor YangTianFu
 * @Date 2019/3/21  18:08
 * 服务器错误
 */
public class ServerException extends  RuntimeException{
    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ServerException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
