package com.thesis.course.minicourse.retrofit.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @autor YangTianFu
 * @Date 2019/3/26  9:22
 * @Description
 */
public class CommonQueryParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("paramsA", "a")
                .addQueryParameter("paramsB", "b")
                .build();
        return chain.proceed(request.newBuilder().url(url).build());
    }
}
