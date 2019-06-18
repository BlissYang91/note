package com.thesis.mentor.retrofit_v2.interceptor;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @autor YangTianFu
 * @Date 2019/3/22  17:25
 * @Description 应用日志拦截器
 */
public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response
        Request request = chain.request();
        //请求发起的时间
        long t1 = System.nanoTime();
        String method = request.method();
        JSONObject jsonObject = new JSONObject();
        if ("POST".equals(method) || "PUT".equals(method)){
            if (request.body() instanceof FormBody){
                FormBody body = (FormBody) request.body();
                if (body != null){
                    for (int i = 0; i < body.size(); i++) {
                        try {
                            jsonObject.put(body.name(i),body.encodedValue(i));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                Log.i("request",String.format("发送请求 %s on %s  %nRequestParams:%s%nMethod:%s",
                        request.url(),chain.connection(),jsonObject.toString(),request.method()));
            }else {
                Buffer buffer = new Buffer();
                RequestBody requestBody = request.body();
                if (requestBody != null){
                    request.body().writeTo(buffer);
                    String body = buffer.readUtf8();
                    Log.i("request", String.format("发送请求 %s on %s  %nRequestParams:%s%nMethod:%s",
                            request.url(), chain.connection(), body, request.method()));
                }
            }
        }else {
            Log.i("request", String.format("发送请求 %s on %s%nMethod:%s",
                    request.url(), chain.connection(), request.method()));
        }

        Response response = chain.proceed(request);
        //收到响应的时间
        long t2 = System.nanoTime();
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.i("request",
                String.format("Retrofit接收响应: %s %n返回json:%s %n耗时：%.1fms",
                        response.request().url(),
                        responseBody.string(),
                        (t2 - t1) / 1e6d
                ));
        return response;
    }
}
