package com.thesis.mentor.retrofit_v2;

import com.thesis.mentor.BuildConfig;
import com.thesis.mentor.api_v2.ApiService;
import com.thesis.mentor.retrofit_v2.interceptor.HeaderInterceptor;
import com.thesis.mentor.retrofit_v2.interceptor.LoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @autor YangTianFu
 * @Date 2019/3/22  17:10
 * @Description Retrofit+RxJava联网的统一管理类
 */
public class RetrofitManager {
    private static volatile RetrofitManager mInstance;
    private static final long DEFAULT_TIMEOUT = 60L;
    private Retrofit retrofit = null;
//    private String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";

    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                if (retrofit == null) {
                    OkHttpClient mClient = new OkHttpClient.Builder()
                            .addInterceptor(new HeaderInterceptor())
                            .addInterceptor(new LoggingInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BuildConfig.LOCALHOST_BASEURL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(mClient)
                            .build();
                }
            }
        }
        return retrofit;
    }
    public ApiService getRequestService() {
        return getRetrofit().create(ApiService.class);
    }
    /**
     * Retrofit上传文件
     */
    public RequestBody getUploadFileRequestBody(String mImagePath) {
        File file = new File(mImagePath);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        return requestBody;
    }

}
