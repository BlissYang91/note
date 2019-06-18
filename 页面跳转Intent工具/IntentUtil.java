package edu.com.gaiwen.firstchoice.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by xfc on 2016/12/28.
 */

public class IntentUtil {
    private static String BUNDLE_KEY = "bundle";

    public static void startActivity(Context context, Class cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    public static void startActivityAndFinishFirst(Context context, Class cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public static void startActivityForResult(Context context, Class cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtra(BUNDLE_KEY, bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Context context, Class cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Context context, Class cls, String key, Object value, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        Bundle bundle = new Bundle();
        if (value.getClass() == String.class) {
            bundle.putString(key, String.valueOf(value));
        } else if (value.getClass() == Integer.class) {
            bundle.putInt(key, (Integer) value);
        } else if (value.getClass() == Boolean.class) {
            bundle.putBoolean(key, (Boolean) value);
        } else {
            bundle.putSerializable(key, (Serializable) value);
        }
        intent.putExtra(BUNDLE_KEY, bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Context context, Class cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtra(BUNDLE_KEY, bundle);
        context.startActivity(intent);
    }

    public static void startActivityAndFinish(Context context, Class cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtra(BUNDLE_KEY, bundle);
        context.startActivity(intent);
        ((Activity) context).finish();
    }


    public static void startActivity(Context context, Class cls, String key, Object value) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        Bundle bundle = new Bundle();
        if (value.getClass() == String.class) {
            bundle.putString(key, String.valueOf(value));
        } else if (value.getClass() == Integer.class) {
            bundle.putInt(key, (Integer) value);
        } else if (value.getClass() == Boolean.class) {
            bundle.putBoolean(key, (Boolean) value);
        } else {
            bundle.putSerializable(key, (Serializable) value);
        }
        intent.putExtra(BUNDLE_KEY, bundle);
        context.startActivity(intent);
    }

    public static Bundle getIntentBundle(Activity activity) {
        if (activity.getIntent().getBundleExtra(BUNDLE_KEY) == null)
            return null;
        return activity.getIntent().getBundleExtra(BUNDLE_KEY);
    }

    public static boolean getIntentBoolean(Activity activity, String key) {
        if (activity.getIntent().getBundleExtra(BUNDLE_KEY) != null)
            return activity.getIntent().getBundleExtra(BUNDLE_KEY).getBoolean(key, false);
        return false;
    }

    public static Serializable getIntentSerializable(Activity activity, String key) {
        if (activity.getIntent().getBundleExtra(BUNDLE_KEY) == null)
            return null;
        return activity.getIntent().getBundleExtra(BUNDLE_KEY).getSerializable(key);
    }

    public static String getIntentString(Activity activity, String key) {
        if (activity.getIntent().getBundleExtra(BUNDLE_KEY) == null)
            return "";
        return activity.getIntent().getBundleExtra(BUNDLE_KEY).getString(key);
    }

    public static int getIntentInt(Activity activity, String key) {
        return activity.getIntent().getBundleExtra(BUNDLE_KEY).getInt(key);
    }

    public static long getIntentLong(Activity activity, String key) {
        return activity.getIntent().getBundleExtra(BUNDLE_KEY).getLong(key);
    }

    public static double getIntentDouble(Activity activity, String key) {
        return activity.getIntent().getBundleExtra(BUNDLE_KEY).getDouble(key);
    }

    public static void startActivityAndFinishLine(Context context, Class cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void startActivityAndFinishLine(Context context, Class cls, String key, String value) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        intent.putExtra(BUNDLE_KEY, bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}

