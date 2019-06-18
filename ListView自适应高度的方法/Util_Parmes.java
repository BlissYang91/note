package com.lab.web.entity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;

/**
 * Created by YTF on 2017/8/10.
 */

public class Util_Parmes {
    /**
     * ListView自适应高度的方法
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChild(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0); // 计算子项View 的宽高

            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        // listView.getDividerHeight()获取子项间分隔符占用的高度

        // params.height最后得到整个ListView完整显示需要的高度

        listView.setLayoutParams(params);

    }

    /**
     * GridView自适应高度的方法
     *
     * @param listView
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setGridViewHeightBasedOnChild(GridView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < (int) listAdapter.getCount()/2+1; i++) { // listAdapter.getCount()返回数据项的数目

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0); // 计算子项View 的宽高

            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = (int) totalHeight/2+(listAdapter.getCount()/2+1)*listView.getVerticalSpacing();
//                + (10 * (listAdapter.getCount()/2 - 1));
//        params.height = totalHeight+420;

        // listView.getDividerHeight()获取子项间分隔符占用的高度

        // params.height最后得到整个ListView完整显示需要的高度

        listView.setLayoutParams(params);

    }

    /**
     * 设置TabLayout下划线宽度
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
//    public static void setIndicator(Context context,TabLayout tabs, int leftDip, int rightDip) {
//        Class<?> tabLayout = tabs.getClass();
//        Field tabStrip = null;
//        try {
//            tabStrip = tabLayout.getDeclaredField("mTabStrip");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//        tabStrip.setAccessible(true);
//        LinearLayout ll_tab = null;
//        try {
//            ll_tab = (LinearLayout) tabStrip.get(tabs);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        int left = (int) (getDisplayMetrics(context).density * leftDip);
//        int right = (int) (getDisplayMetrics(context).density * rightDip);
//
//        for (int i = 0; i < ll_tab.getChildCount(); i++) {
//            View child = ll_tab.getChildAt(i);
//            child.setPadding(0, 0, 0, 0);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//            params.leftMargin = left;
//            params.rightMargin = right;
//            child.setLayoutParams(params);
//            child.invalidate();
//        }
//    }
//    public static DisplayMetrics getDisplayMetrics(Context context) {
//        DisplayMetrics metric = new DisplayMetrics();
//        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
//        return metric;
//    }
//
//    public static float getPXfromDP(float value, Context context) {
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
//                context.getResources().getDisplayMetrics());
//    }
}
