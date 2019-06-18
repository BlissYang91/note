package com.thesis.course.minicourse.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.thesis.course.minicourse.utils.ToastUtil;

import androidx.annotation.Nullable;

/**
 * @autor YangTianFu
 * @Date 2019/3/25  13:35
 * @Description
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(WXPayEntryActivity.this, null);
        api.registerApp("wx26e8f22f6b70b990");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent,this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.i(TAG, "onReq: "+baseReq.checkArgs());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.i(TAG, "onResp: "+baseResp.errCode + "///" +baseResp.getType());
        if (baseResp.errCode == 0) {
            WXPayEntryActivity.this.finish();
        }else if (baseResp.errCode==-2){
            ToastUtil.show(this,"用戶取消！");
            WXPayEntryActivity.this.finish();
        }else if(baseResp.errCode==-1){
            ToastUtil.show(this,"微信支付失败！");
            WXPayEntryActivity.this.finish();
        }
    }
}
