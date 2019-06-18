package edu.com.gaiwen.firstchoice.network;

import android.content.Context;

import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import edu.com.gaiwen.firstchoice.base.BaseActivityxfc;
import edu.com.gaiwen.firstchoice.model.User;
import edu.com.gaiwen.firstchoice.utils.LogUtil;
import edu.com.gaiwen.firstchoice.utils.NullStringToEmptyAdapterFactory;
import edu.com.gaiwen.firstchoice.utils.ToastUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by xfc on 2018/1/27.
 * rxjava2.0 工具类处理
 */

public class Myobserver implements Observer<ResponseBody> {
    private BaseActivityxfc baseActivityxfc;
    private Class<?> clazz;

    public Myobserver(Context context) {
        baseActivityxfc = (BaseActivityxfc) context;
        this.clazz = null;
    }

    public Myobserver(Context context, Class<?> clazz) {
        baseActivityxfc = (BaseActivityxfc) context;
        this.clazz = clazz;
    }

    @Override
    public void onSubscribe(Disposable d) {
        baseActivityxfc.addDisposable(d);
        LogUtil.eLength("Disposable", d + "");
    }

    @Override
    public void onNext(ResponseBody response) {
        ServerOperation serverOperation = new ServerOperation(response);
        String back = serverOperation.setSeverflag();
        if (back.equals("")) {
            onFailure();
        } else {
            if (clazz == null) {
                User user = new GsonBuilder().registerTypeAdapterFactory(
                        new NullStringToEmptyAdapterFactory()).create().fromJson(back, User.class);
                onSuccess(user);
            } else {
                Object object = new GsonBuilder().registerTypeAdapterFactory(
                        new NullStringToEmptyAdapterFactory()).create().fromJson(back, clazz);
                onSuccess(object);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.eLength("Throwable", e + "");
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

    @Override
    public void onComplete() {
        LogUtil.eLength("onComplete", "xfc");
    }

    public void onFailure() {
    }

    public void onSuccess(Object object) {
    }
}
