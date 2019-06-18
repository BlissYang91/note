package edu.com.gaiwen.firstchoice.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.com.gaiwen.firstchoice.config.Config;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by xfc on 2017/7/24.
 * 邮箱:874020287@qq.com
 */

public class RetrofitUtil {
    public static final long DEFAULT_TIMEOUT = 15;
    private static RetrofitUtil retrofitUtil;

    public synchronized static RetrofitUtil getInstance() {
        if (retrofitUtil == null) {
            retrofitUtil = new RetrofitUtil();
        }
        return retrofitUtil;
    }

    /**
     * 提交表单数据
     */
    public ApiService setForm() {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        OkHttpClient client = null;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.eLength("提交数据", message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    /**
     * 提交文件设置(多文件上传)
     */
    public static Map<String, RequestBody> setFile(Map<String, Object> map, String filename, File file) {
        Map<String, RequestBody> fileUpload = new HashMap<>();
        MediaType textType = MediaType.parse("text/plain");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            fileUpload.put(entry.getKey().toString(), RequestBody.create(textType, entry.getValue().toString()));
        }
        RequestBody fileio = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        LogUtil.eLength("文件名", fileio + "");
        fileUpload.put("file\"; filename=\"" + filename, fileio);
        return fileUpload;
    }

    /**
     * 单个文件上传
     */
    public static MultipartBody.Part setFile(File file) {
        //构建requestbody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //将resquestbody封装为MultipartBody.Part对象
        //string name 为对应上传的字段名
        MultipartBody.Part body = MultipartBody.Part.createFormData("myfiles", file.getName(), requestFile);
        return body;
    }

    /**
     * 提交json设置
     */
    public static RequestBody setBodyjson(String bodyjson) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyjson);
        return body;
    }
}
