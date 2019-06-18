package com.thesis.mentor.retrofit_v2.interceptor;

import android.util.Log;

import com.thesis.mentor.application.MyApplication;
import com.thesis.mentor.uitl_v2.NetUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @autor YangTianFu
 * @Date 2019/3/26  9:26
 * @Description 设置缓存的拦截器
 */
public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request =  chain.request();
        if (!NetUtils.isNetworkConnected(MyApplication.getApplicationContext1())){
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        Response response = chain.proceed(request);
        if (NetUtils.isNetworkConnected(MyApplication.getApplicationContext1())){
            String cacheControl = request.cacheControl().toString();
            Log.e("net","网络连接正常");
            return response.newBuilder().header("Cache-Control",cacheControl)
                    .removeHeader("Pragma").build();
        }else {
            Log.e("net","断网了");
            return  response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + "60 * 60 * 24 * 7")
                    .removeHeader("Pragma").build();
        }
    }
}
