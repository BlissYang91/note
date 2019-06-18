package com.thesis.course.minicourse.retrofit.interceptor;

import android.util.Log;

import com.thesis.course.minicourse.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * @autor YangTianFu
 * @Date 2019/3/26  9:17
 * @Description 添加请求头需要携带的参数
 */
public class HeaderInterceptor implements Interceptor {
    //请求头信息
    private final String HEADER_CONNECTION = "keep-alive";
    String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.i(TAG, "HeaderInterceptor: "+request.toString());
        LogUtil.i("HeaderInterceptor: "+request.toString());
        Request requestBuilder = request.newBuilder()
                .addHeader("Connection",HEADER_CONNECTION)
                .addHeader("User-Agent",userAgent)
                .method(request.method(),request.body())
                .build();
        return chain.proceed(requestBuilder);
    }
}
