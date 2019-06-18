package edu.com.gaiwen.firstchoice.network;


import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import edu.com.gaiwen.firstchoice.model.User;
import edu.com.gaiwen.firstchoice.utils.LogUtil;
import edu.com.gaiwen.firstchoice.utils.NullStringToEmptyAdapterFactory;
import edu.com.gaiwen.firstchoice.utils.StringUtil;
import edu.com.gaiwen.firstchoice.utils.ToastUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xfc on 2017/7/25.
 * 邮箱:874020287@qq.com
 */

public abstract class Mycallback implements Callback<ResponseBody> {
    private Class<?> clazz;

    public Mycallback() {
        this.clazz = null;
    }

    public Mycallback(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            if (response.body() != null) {
                String back = response.body().string();
                if (!back.equals("") && back != null) {
                    if (back.indexOf("code") > 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(back).getJSONObject("data");
                            String code = jsonObject.getString("code");
                            LogUtil.eLength("数据处理code", code);
                            if (code.equals("0")) {
                                if (clazz == null) {
                                    User user = new GsonBuilder().registerTypeAdapterFactory(
                                            new NullStringToEmptyAdapterFactory()).create().fromJson(back, User.class);
                                    onSuccess(user);
                                } else {
                                    Object object = new GsonBuilder().registerTypeAdapterFactory(
                                            new NullStringToEmptyAdapterFactory()).create().fromJson(back, clazz);
                                    onSuccess(object);
                                }
                            } else if (code.equals("2")) {
                                ToastUtil.showToast("请重新登录账号");
                                onFailure();
                            } else {
                                String msg = jsonObject.getString("msg");
                                if (StringUtil.isnotEmpty(msg)) {
                                    ToastUtil.showToast(msg);
                                } else {
                                    ToastUtil.showToast("请求失败");
                                }
                                onFailure();
                            }
                        } catch (JSONException e) {
                            LogUtil.eLength("数据处理code", "解析异常");
                            e.printStackTrace();
                            onFailure();
                            ToastUtil.showToast("数据异常");
                        }
                    } else {
                        if (back.indexOf("ret") > 0) {
                            try {
                                JSONObject jsonObject = new JSONObject(back);
                                String ret = jsonObject.getString("ret").trim();
                                if (ret.equals("400")) {
                                    ToastUtil.showToast("参数传递错误");
                                } else if (ret.equals("500")) {
                                    ToastUtil.showToast("服务器内部错误");
                                } else {
                                    ToastUtil.showToast("未知异常");
                                }
                                onFailure();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtil.showToast("数据解析异常");
                            onFailure();
                        }
                    }
                }
            }
        } catch (IOException e) {
            ToastUtil.showToast("数据异常");
            LogUtil.eLength("数据处理code", "未拿到数据");
            e.printStackTrace();
            onFailure();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable e) {
        if (e != null) {
            if (e instanceof UnknownHostException || e instanceof ConnectException) {
                ToastUtil.showToast("网络不可用！请检查网络连接！");
            } else if (e instanceof SocketTimeoutException) {
                ToastUtil.showToast("请求超时！");
            } else if (e instanceof SocketException) {
                ToastUtil.showToast("连接服务器失败！请稍后再试！");
            } else if (e instanceof IOException) {
                ToastUtil.showToast("连接服务器失败！请稍后再试！！");
            } else {
                ToastUtil.showToast("连接服务器失败！请稍后再试！");
            }
        }
        onFailure();
    }

    public void onFailure() {
    }

    public abstract void onSuccess(Object object);
}
