package com.by.skin_core.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @autor YangTianFu
 * @Date 2019/4/12 17:20
 * @Description 日志保存SD卡并上传服务器的工具类
 */
public class LogUtil {
    private static final String TAG = LogUtil.class.getSimpleName();
    // 是否需要打印bug，在application的onCreate函数里面初始化
    public static boolean isDebug;
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/CrashKZ/log/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".txt";
    private static Context mContext;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
            dumpLogErrorToSDCard(msg);
        }
    }

    public static void d(String tag, String msg) {
        showLogCompletion(tag,msg);
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
            dumpLogErrorToSDCard(msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
            dumpLogErrorToSDCard(msg);
        }
    }

    public static void v(String msg) {
        if (isDebug) {
            Log.v(TAG, msg);
        }
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
            dumpLogErrorToSDCard(msg);
        }
    }

    /**
     * 截断输出日志
     *
     * @param msg
     */
    public static void eLength(String tag, String msg) {
        if (isDebug) {
            if (tag == null || tag.length() == 0 || msg == null || msg.length() == 0) {
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
    
    public static void printJson(String tag, String msg, String headString) {
        

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, "║ " + line);
        }
        printLine(tag, false);
    }

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    /**
     * 分段打印出较长log文本
     *
     * @param log 原log文本
     * @param
     */
    public static void showLogCompletion(String tag, String log) {
        if (log.length() > 4000) {
            for (int i = 0; i < log.length(); i += 4000) {
                if (i + 4000 < log.length()) {
                    Log.d(tag + i, log.substring(i, i + 4000));
                } else {
                    Log.d(tag + i, log.substring(i, log.length()));
                }
            }
        } else {
            Log.d(tag, log);
        }
    }

    /**
     * @Author：yangtianfu @Date：{2019/4/16 16:36}
     * @Description 将崩溃日志和自定义错误日志写入SD卡根目录
     */
    public static void dumpLogErrorToSDCard(String msg) {
        // 如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (isDebug) {
                Log.e(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date(current));
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(PATH + FILE_NAME + FILE_NAME_SUFFIX);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println(msg);
            pw.println();
            pw.close();
            Log.e(TAG, "日志写入成功");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, "日志写入失败");
        }
    }

    /**
     * @Author：yangtianfu @Date：{2019/4/16 16:37}
     * @Description 获取当前设备信息
     */
    private static void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        // Android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.print(Build.VERSION.SDK_INT);
        // 手机制造商
        pw.print("Vendor: ");
        pw.print(Build.MANUFACTURER);
        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        // CPU架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }
}

