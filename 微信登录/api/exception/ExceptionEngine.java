package com.thesis.course.minicourse.api.exception;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import org.json.JSONException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * @autor YangTianFu
 * @Date 2019/3/21  18:11
 * 错误/异常处理
 */
public class ExceptionEngine {
    public static final int UN_KNOWN_ERROR = 1000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;//解析(服务器)数据错误
    public static final int CONNECT_ERROR = 1003;//网络连接错误
    public static final int TIME_OUT_ERROR = 1004;//网络连接超时

    public static ApiException handleException(Throwable e) {

        Log.i("error", e.toString());

        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpExc = (HttpException) e;
            ex = new ApiException(e, httpExc.code());
            ex.setMsg("网络错误");
            return ex;
        } else if (e instanceof ServerException) {

            ServerException serverExc = (ServerException) e;
            ex = new ApiException(serverExc, serverExc.getCode());
            ex.setMsg(serverExc.getMsg());

            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {
            ex = new ApiException(e, ANALYTIC_SERVER_DATA_ERROR);
            ex.setMsg("请求错误");
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, CONNECT_ERROR);
            ex.setMsg("连接失败");
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, TIME_OUT_ERROR);
            ex.setMsg("网络超时");
            return ex;
        } else {  //未知错误
            ex = new ApiException(e, UN_KNOWN_ERROR);
            ex.setMsg("请求错误");
            return ex;
        }
    }
}
