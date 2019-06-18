package com.energysh.drawshow.util;


/**
 * Created by anting.hu on 2016/9/4.
 https://github.com/orhanobut/logger
 */

import android.util.Log;

import com.energysh.drawshow.BuildConfig;

public class xLog {

    public static final boolean DEBUG = BuildConfig.LOG;

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void s(String tag, String msg){
        if (DEBUG){
            System.out.println(tag + ": " + msg);
        }
    }
}
