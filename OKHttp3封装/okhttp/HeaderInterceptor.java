package com.app.ytf.httpdemo.okhttp;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author ytf
 * 自定义拦截器
 * Chain 对象可以拿到当前请求的 Request 对象，然后我们可以对Request做二次处理，
 * 最后生成我们需要的请求，然后再通过网络发送请求到服务端，这样就完成了一次拦截
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Logger.d(String.format("Sending request %s on %s%n%s", request.url(),  chain.connection(), request.headers()));

        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        Logger.d(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Logger.json(content);
        Response response1=chain.proceed(request);
        Logger.d("返回response：",response1.toString());
        return response.newBuilder()
                .header("Authorization", "请求头授权信息拦截")
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();


    }
}
