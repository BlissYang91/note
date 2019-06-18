package com.lib.core.baseapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import java.util.LinkedList;

/**
 * APPLICATION
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    private static int activityCounter = 0;
    private static LinkedList<Activity> mActivityLinkedList;
    private ActivityLifecycleCallbacksImpl mActivityLifecycleCallbacksImpl;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        mActivityLinkedList = new LinkedList<>();
        mActivityLifecycleCallbacksImpl = new ActivityLifecycleCallbacksImpl();
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacksImpl);

    }


    //判断App是否在后台运行
    public boolean isAppRunningBackground() {
        boolean flag = false;
        if (activityCounter == 0) {
            flag = true;
        }
        return flag;
    }

    //退出应用
    public void finishAllActivity() {
        if (null != mActivityLinkedList) {
            for (Activity activity : mActivityLinkedList) {
                if (null != activity) {
                    activity.finish();
                }
            }
        }
    }

    //将activity全部关闭掉,除掉cls
    public void finishOther(Class<?>... cls) {
        if (null != mActivityLinkedList && cls != null) {
            LinkedList<String> clsList = new LinkedList<>();
            for (Class c : cls) {
                clsList.add(c.getSimpleName());
            }
            for (Activity activity : mActivityLinkedList) {
                if (!clsList.contains(activity.getClass().getSimpleName())) {
                    activity.finish();
                }
            }
        }
    }

    //关闭指定activity
    public void finishActivity(Class<?> cls) {
        if (null != mActivityLinkedList && cls != null) {
            for (Activity activity : mActivityLinkedList) {
                if (activity.getClass().getSimpleName().equals(cls.getSimpleName())) {
                    activity.finish();
                }

            }
        }
    }

    private class ActivityLifecycleCallbacksImpl implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (null != mActivityLinkedList && null != activity) {
                mActivityLinkedList.addFirst(activity);
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityCounter++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityCounter--;
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (null != mActivityLinkedList && null != activity) {
                if (mActivityLinkedList.contains(activity)) {
                    mActivityLinkedList.remove(activity);
                    activity.finish();
                    activity = null;
                }
            }
        }

    }

}
