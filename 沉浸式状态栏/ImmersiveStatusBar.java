package com.lab.web.entity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

/**
 * Created by YTF on 2017/8/15.
 */

public class ImmersiveStatusBar {
    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     *
     */
    public static void initStateInAcvitity(Activity activity, LinearLayout linear_bar) {

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //透明导航栏
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.ll_bar);

            //获取到状态栏的高度
            int statusHeight = getStatusBarHeightInAcvitity(activity);
            //动态的设置隐藏布局的高度
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
            linear_bar.setVisibility(View.VISIBLE);
        }
    }
    public static void initStateInAcvitityParLiner(Activity activity, LinearLayout linear_bar) {

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //透明导航栏
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.ll_bar);

            //获取到状态栏的高度
            int statusHeight = getStatusBarHeightInAcvitity(activity);
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
            linear_bar.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeightInAcvitity(Activity activity) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 动态的设置Fragment状态栏  实现沉浸式状态栏
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void initStateInFragment(Fragment fragment, LinearLayout linear_bar) {

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明状态栏
//            fragment.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            fragment.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

//            fragment.getActivity().getWindow().setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //透明导航栏
//            fragment.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //
//            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.ll_bar);

            //获取到状态栏的高度
            int statusHeight = getStatusBarHeightInFragment(fragment);
            //动态的设置隐藏布局的高度
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
//            linear_bar.setBackground(fragment.getActivity().getDrawable(android.R.drawable.bottom_bar));
            linear_bar.setVisibility(View.VISIBLE);
        }
    }
//    public static void initStateInFragmentLL(Fragment fragment, LinearLayout linear_bar) {
//
//        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            fragment.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明状态栏
////            fragment.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//            fragment.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
////            fragment.getActivity().getWindow().setFlags(
////                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
////                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            //透明导航栏
////            fragment.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            //
////            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.ll_bar);
//
//            //获取到状态栏的高度
//            int statusHeight = getStatusBarHeightInFragment(fragment);
//            //动态的设置隐藏布局的高度
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
//            params.height = statusHeight;
//            linear_bar.setLayoutParams(params);
////            linear_bar.setBackground(fragment.getActivity().getDrawable(android.R.drawable.bottom_bar));
//            linear_bar.setVisibility(View.VISIBLE);
//        }
//    }

    /**
     * 通过反射的方式获取Fragment状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeightInFragment(Fragment fragment) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return fragment.getActivity().getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 判断api级别，设置透明状态栏
     * @param on
     */
    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity,boolean on) {
        Window win =activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits; }
        else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
