package com.bliss.yang.nicewords.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.bliss.yang.nicewords.APP;

/**
 * @author YangTianFu
 * @date 2019/6/21  10:59
 * @description 资源获取工具类
 */
public class ResUtil {
    private static Context mContext;

    public static Context getApplicationContext() {
        return APP.getInstance();
    }

    public static void init() {
        mContext = getApplicationContext();
    }

    public static String getString(int resId) {
        return mContext.getResources().getString(resId);
    }

    public static String[] getStringArray(int array) {
        return mContext.getResources().getStringArray(array);
    }

    public static int getColor(int colorId) {
        return mContext.getResources().getColor(colorId);
    }

    public static Drawable getDrawable(int drawableId) {
        return mContext.getResources().getDrawable(drawableId);
    }

    public static ColorStateList getColorStateList(int resId) {
        return mContext.getResources().getColorStateList(resId);
    }

    public static void showToast(Context context, CharSequence charSequence) {
        if (context != null) {
            Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
        }
    }
}

/***********************************使用***************************************** */

mPresenter.showMessage(ResUtil.getString(R.string.request_version_error));

