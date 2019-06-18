package com.lab.web.entity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ppg on 2017/12/19.
 */

public class CheckApkExist {
    private static String facebookPkgName = "com.tencent.android.qqdownloader ";//应用宝包名

    public static boolean checkApkExist(Context context, String packageName){
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
//            Timber.d(info.toString());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
//          Timber.d(e.toString());
            return false;
        }
    }

    public static boolean checkFacebookExist(Context context){
        return checkApkExist(context, facebookPkgName);
    }

    public static boolean isAppInstalled(Context context,String packagename)
    {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        }catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo ==null){
            //System.out.println("没有安装");
            return false;
        }else{
            //System.out.println("已经安装");
            return true;
        }
    }

}
