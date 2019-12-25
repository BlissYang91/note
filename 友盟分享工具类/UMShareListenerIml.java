package com.thesis.course.minicourse.umeng;

import android.util.Log;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class UMShareListenerIml implements UMShareListener {
    private static final String TAG = "UMShareListenerIml";
    private static UMShareListenerIml instance = new UMShareListenerIml();

    public static UMShareListenerIml getInstance() {
        return instance;
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.e(TAG, "onStart: 分享开始");
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Log.e(TAG, "onStart: 分享成功" );
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.e(TAG, "onStart: 分享失败" + throwable.toString());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.e(TAG, "onStart: 分享取消" );
    }
}
