package com.app.ytf.httpdemo.exception;

import android.net.ParseException;
import android.util.Log;
import android.util.MalformedJsonException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ExceptionEngine {
    private static final String TAG = "ExceptionEngine_ERROR";
    public static final int UN_KNOWN_ERROR = 1000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;//解析(服务器)数据错误
    public static final int ANALYTIC_CLIENT_DATA_ERROR = 1002;//解析(客户端)数据错误
    public static final int CONNECT_ERROR = 1003;//网络连接错误
    public static final int TIME_OUT_ERROR = 1004;//网络连接超时
    public static final int TIOException_ERROR = 1005;//IOException

    public static ApiException catchException(Throwable e) {
        Log.e(TAG, "自定义异常捕获：" + e.toString());

        ApiException ex = null;
        if (e != null) {
            if (e instanceof UnknownHostException || e instanceof ConnectException) {
                ex = new ApiException(e, CONNECT_ERROR);
                ex.setMsg("网络不可用！请检查网络连接！");
                return ex;
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException || e instanceof MalformedJsonException) {
                ex = new ApiException(e, ANALYTIC_SERVER_DATA_ERROR);
                ex.setMsg("请求错误");
                return ex;
            } else if (e instanceof SocketTimeoutException) {
                ex = new ApiException(e, TIME_OUT_ERROR);
                ex.setMsg("网络连接超时");
            } else if (e instanceof SocketException) {
                ex = new ApiException(e, ANALYTIC_SERVER_DATA_ERROR);
                ex.setMsg("连接服务器失败！请稍后再试！");
            } else if (e instanceof IOException) {
                ex = new ApiException(e, TIOException_ERROR);
                ex.setMsg("IOException连接服务器失败！请稍后再试！");
                return ex;
            } else if (e instanceof ServerException) {
                ServerException serverExc = (ServerException) e;
                ex = new ApiException(serverExc, serverExc.getCode());
                ex.setMsg(serverExc.getMsg());
                return ex;
            } else {
                ex = new ApiException(e, UN_KNOWN_ERROR);
                ex.setMsg("未知错误！");
                return ex;
            }
        }
        return ex;

    }
}
