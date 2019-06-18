package com.necsthz.sevnton.network;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.necsthz.sevnton.SevntonApplication;
import com.necsthz.sevnton.model.User;
import com.necsthz.sevnton.utils.LogUtil;
import com.necsthz.sevnton.utils.NullStringToEmptyAdapterFactory;
import com.necsthz.sevnton.utils.SharePreferenceUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 与后台协商拿到数据的判断，异步封装
 */

public abstract class Mycalllback implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {
        Message msg = new Message();
        msg.arg1 = 404;
        msg.arg2 = -1;
        msg.what = 1;
        msg.obj = "网络错误,请检查网络设置";
        handler.sendMessage(msg);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Message msg = new Message();
        try {
            String back = response.body().string();
            if (response.code() == 200) {
                ResponseData gpResponse = ResponseData.createResponse(back);
                LogUtil.eLength("走入200", "");
                if (gpResponse != null && gpResponse.code == 0) {
                 
                    msg.what = 0;
                    msg.obj = user;
                    handler.sendMessage(msg);
                    LogUtil.eLength("发送0", "");
//                    onSuccess(gpResponse.data);
                } else {
                    LogUtil.eLength("为空", "");
                    msg.arg1 = response.code();
                    msg.arg2 = gpResponse.code;
                    msg.what = 1;
                    msg.obj = gpResponse.msg;
                    handler.sendMessage(msg);
//                    onFailure(gpResponse.msg,response.code(),gpResponse.code);
                }
            } else {
                LogUtil.eLength("未走入200", "");
                msg.arg1 = response.code();
                msg.arg2 = -1;
                msg.what = 1;
                msg.obj = response.message();
                handler.sendMessage(msg);
//                onFailure(response.message(),response.code(),-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            msg.arg1 = response.code();
            msg.arg2 = -1;
            msg.what = 1;
            msg.obj = response.message();
            handler.sendMessage(msg);
//            onFailure(e.getMessage(),response.code(),-1);
        }
    }

    public abstract void onSuccess(User user);

    public abstract void onFailure(Object message, int netCode, int code);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LogUtil.eLength("成功走入", "");
                
                    onSuccess(user);
                    break;
                case 1:
                    if (msg.arg2 == 3) {
                        msg.obj = "请登录账号";
                        SharePreferenceUtil.setValue(SevntonApplication.context, SharePreferenceUtil.LOGIN_FLAG, false);
                        SharePreferenceUtil.setValue(SevntonApplication.context, SharePreferenceUtil.USER_ID, "");
                        //SharePreferenceUtil.clearData();
                        LogUtil.eLength("查看信息", "查看情况");
                    } else if (msg.arg2 == 99 || msg.arg2 == 1) {
                        msg.obj = "请登录账号";
                        SharePreferenceUtil.setValue(SevntonApplication.context, SharePreferenceUtil.LOGIN_FLAG, false);
                        SharePreferenceUtil.setValue(SevntonApplication.context, SharePreferenceUtil.USER_ID, "");
                        LogUtil.eLength("方法进入", "查看情况");
                    }
                    if (msg.obj != null && !TextUtils.isEmpty(msg.obj.toString())) {
                        Toast.makeText(SevntonApplication.context, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    }
                    onFailure(msg.obj, msg.arg1, msg.arg2);
                    break;
            }
        }
    };
}
