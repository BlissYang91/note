package com.necsthz.sevnton.network;

import com.necsthz.sevnton.SevntonApplication;
import com.necsthz.sevnton.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Zhangyuedong on 16/10/10.
 */

public class RequestStart {
    static OkHttpClient client = null;
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static void begin(RequestParameter parameter, Mycalllback mycalllback) {
//        OkHttpClient client;
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        final InputStream inputStream;
        if (client == null) {
            try {
                inputStream = SevntonApplication.context.getAssets().open("ServerCertificate.cer"); // 得到证书的输入流
                try {
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            LogUtil.eLength("提交数据", message);
                        }
                    });
                    //httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    trustManager = trustManagerForCertificates(inputStream);//以流的方式读入证书
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, new TrustManager[]{trustManager}, null);
                    sslSocketFactory = sslContext.getSocketFactory();
                    client = new OkHttpClient.Builder()
                            .sslSocketFactory(sslSocketFactory)
                            .addInterceptor(httpLoggingInterceptor)
                            .build();
                } catch (GeneralSecurityException e) {
                    client = new OkHttpClient();
                    throw new RuntimeException(e);
                }
//                start(client,parameter,handler);
            } catch (IOException e) {
                client = new OkHttpClient();
                e.printStackTrace();
            }
        }
        LogUtil.eLength("数据问题", parameter.getParameter() + "");
        Request request = new Request.Builder()
                .url(parameter.getUrl())
                .post(parameter.getParameter())
                .build();
        Call call = client.newCall(request);
        call.enqueue(mycalllback);
    }

    public static void begin(String strurl, Map<String, Object> map, Mycalllback mycalllback) {
//        OkHttpClient client;
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        final InputStream inputStream;

        if (client == null) {
            try {
                inputStream = SevntonApplication.context.getAssets().open("ServerCertificate.cer"); // 得到证书的输入流
                try {
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            LogUtil.eLength("提交数据", message);
                        }
                    });
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    trustManager = trustManagerForCertificates(inputStream);//以流的方式读入证书
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, new TrustManager[]{trustManager}, null);
                    sslSocketFactory = sslContext.getSocketFactory();
                    client = new OkHttpClient.Builder()
                            .addInterceptor(httpLoggingInterceptor)
                            .sslSocketFactory(sslSocketFactory)
                            .build();
                } catch (GeneralSecurityException e) {
                    client = new OkHttpClient();
                    throw new RuntimeException(e);
                }
//                start(client,parameter,handler);
            } catch (IOException e) {
                client = new OkHttpClient();
                e.printStackTrace();
            }
        }
        StringBuffer sb = new StringBuffer();
        //设置表单参数
        for (String key : map.keySet()) {
            sb.append(key + "=" + map.get(key) + "&");
        }
        LogUtil.showLogCompletion("传入数据", sb.toString());
        RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, sb.substring(0, sb.length()));
        Request request = new Request.Builder()
                .url(strurl)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(mycalllback);
    }


    

}
