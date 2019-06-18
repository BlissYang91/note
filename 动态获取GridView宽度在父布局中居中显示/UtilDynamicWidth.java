package com.lab.web.entity;

/**
 * Created by YTF on 2017/8/11.
 */


import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.lab.web.view.DensityUtil;

/**
 * 动态获取GridView的宽度并在父布局中居中显示
 */
public class UtilDynamicWidth{
    /**
     * @param context 调用者所在的内容对象
     * @param mContentGv 要处理的GridView对象
     * @param num 要处理的GridView中item的数量
     * @param withdp 每个item的宽度
     * @param withpx 当前手机屏幕的宽度px
     */
    public static void dynamicWith(Context context,GridView mContentGv, int num,double withdp,float withpx) {
        LinearLayout.LayoutParams params=null;
//        int with= context.getWindowManager().getDefaultDisplay().getWidth();//获取手机屏幕宽度px
        int withdip= px2dip(context,withpx);//dip
//        int size = img_tequan.length;
        // item宽度Gridview宽度在这里动态设置的

        int shang= withdip /withdip;
        int itemWidth = dip2px(context, withdip);
        int totalWidth=0;
        if (shang>num){//item数量小于可以显示的最大容量
            totalWidth= itemWidth*num;
            mContentGv.setGravity(Gravity.CENTER_HORIZONTAL);
        }else if(shang<=num) {
//            itemWidth=DensityUtil.px2dip(this, 85);
            totalWidth=itemWidth*num+num;
        }
        // item之间的间隔
//        int itemPaddingH = DensityUtil.dip2px(this, 1);

        // 计算GridView宽度 size * (itemWidth + itemPaddingH)
        int gridviewWidth = totalWidth;

        params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        mContentGv.setLayoutParams(params);
        mContentGv.setColumnWidth(itemWidth);
//        mContentGv.setHorizontalSpacing(itemPaddingH);
        mContentGv.setStretchMode(GridView.NO_STRETCH);
        mContentGv.setNumColumns(num);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     *
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
