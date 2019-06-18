package cn.kudesoft.daka.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.kudesoft.daka.R;

/**
 * @author YangTianFu
 * @date 2019/6/18  12:02
 * @description 自定义布局显示dialog
 */
public class MyDialog extends Dialog {

    public MyDialog(Context context, int width, int height, View layout, int style) {
        super(context,style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public MyDialog(Context context,  Bitmap bitmap) {
        super(context,R.style.MyDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        setContentView(view);
        ImageView imageView = view.findViewById(R.id.iv_scan);
        Glide.with(context).asBitmap().load(bitmap).into(imageView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }
}
/***********************************style************************************************* */
// <!--自定义Dialog背景全透明无边框theme-->
//     <style name="MyDialog" parent="android:style/Theme.Dialog">
//         <!--背景颜色和透明程度-->
//         <item name="android:windowBackground">@android:color/transparent</item>
//         <!--是否去除标题-->
//         <item name="android:windowNoTitle">true</item>
//         <item name="android:background">@android:color/transparent</item>
//         <!--是否去除边框-->
//         <item name="android:windowFrame">@null</item>
//         <!--是否浮现在activity之上-->
//         <item name="android:windowIsFloating">true</item>
//         <!--是否模糊-->
//         <item name="android:backgroundDimEnabled">true</item>
//         <!-- 半透明 -->
//         <item name="android:windowIsTranslucent">true</item>
//         <!-- 遮罩层 -->
//         <item name="android:backgroundDimAmount">0.5</item>

//     </style>
/**************************************调用************************************************* */
// MyDialog dialog = new MyDialog(this,bitmap);
// dialog.setCancelable(true);
// dialog.show();
/****************************************dialoglayout************************************************** */
// <?xml version="1.0" encoding="utf-8"?>
// <LinearLayout
//     xmlns:android="http://schemas.android.com/apk/res/android"
//     android:orientation="vertical"
//     android:layout_width="200dp"
//     android:layout_height="200dp"
//     android:alpha="0.8"
//     android:gravity="center">
//     <ImageView
//         android:id="@+id/iv_scan"
//         android:layout_width="200dp"
//         android:layout_height="200dp"
//         android:scaleType="centerInside"
//         android:src="@mipmap/logo"/>

// </LinearLayout>