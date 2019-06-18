package com.lab.web.entity;

import android.util.Log;

/**
 * Created by xfc on 2017-01-05.
 */

/**
 * log打印工具类 https://github.com/orhanobut/logger
 */
public class LogUtil {
    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug;// 是否需要打印bug，在application的onCreate函数里面初始化
    private static final String TAG = "tag";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug){
            Log.i(TAG, msg);
        }

    }

    public static void d(String msg) {
        if (isDebug){
            Log.d(TAG, msg);
        }

    }

    public static void e(String msg) {
        if (isDebug){
            Log.e(TAG, msg);
        }

    }

    public static void v(String msg) {
        if (isDebug){
            Log.v(TAG, msg);
        }

    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug){
            Log.i(tag, msg);
        }

    }

    public static void d(String tag, String msg) {
        if (isDebug){
            Log.i(tag, msg);
        }

    }

    public static void e(String tag, String msg) {
        if (isDebug){
            Log.i(tag, msg);
        }

    }

    public static void v(String tag, String msg) {
        if (isDebug){
            Log.i(tag, msg);
        }

    }

    /**
     * 截断输出日志
     *
     * @param msg
     */
    public static void eLength(String tag, String msg) {
        if (isDebug) {
            if (tag == null || tag.length() == 0
                    || msg == null || msg.length() == 0){
                return;
            }


            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize) {// 长度小于等于限制直接打印
                Log.e(tag, msg);
            } else {
                while (msg.length() > segmentSize) {// 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    Log.e(tag, logContent);
                }
                Log.e(tag, msg);// 打印剩余日志
            }
        }
    }

    /**
     * 分段打印出较长log文本
     *
     * @param log 原log文本
     * @param
     */
    public static void showLogCompletion(String string, String log) {
        if (log.length() > 4000) {
            for (int i = 0; i < log.length(); i += 4000) {
                if (i + 4000 < log.length()){
                    Log.i(string + i, log.substring(i, i + 4000));
                }

                else{
                    Log.i(string + i, log.substring(i, log.length()));
                }

            }
        } else{
            Log.i(string, log);
        }
    }

}
