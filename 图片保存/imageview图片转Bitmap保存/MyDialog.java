package cn.kudesoft.daka.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.kudesoft.daka.R;
import cn.kudesoft.daka.eventbus.EventBusUtil;

/**
 * @author YangTianFu
 * @date 2019/6/18  12:02
 * @description 自定义显示dialog
 */
public class MyDialog extends Dialog {
    private static final String TAG = "MyDialog";
    public MyDialog(Context context, int width, int height, View layout, int style) {
        super(context,style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public MyDialog(final Context context, Bitmap bitmap) {
        super(context,R.style.MyDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        setContentView(view);
        final ImageView imageView = view.findViewById(R.id.iv_scan);
        Glide.with(context).asBitmap().load(bitmap).into(imageView);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showBottomDialog(context,imageView);
                return false;
            }
        });
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    private void showBottomDialog(Context context, final ImageView imageView) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(context);
        //2、设置布局
        View view = View.inflate(context,R.layout.dialog_custom_layout,null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
//        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                picUrl = hitTestResult.getExtra();//获取图片链接
//                            保存图片到相册
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        urlToBitMap(picUrl);
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        EventBus.getDefault().post(bitmap);
                        Log.e(TAG, "run: 保存图片到相册");
                    }
                }).start();
            }
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "run: 取消");
                dialog.dismiss();
            }
        });
    }

    public MyDialog(Context context,boolean faceResult) {
        super(context,R.style.MyDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_face_layout, null);
        setContentView(view);
        ImageView imageView = view.findViewById(R.id.iv_face_allow);
        TextView textView = view.findViewById(R.id.tv_face_result);
        Log.e(TAG, "MyDialog: faceResult == " +faceResult );
        if (faceResult){
            imageView.setImageResource(R.mipmap.access);
            textView.setText("认证通过！");
            textView.setTextColor(Color.GREEN);
        }else {
            imageView.setImageResource(R.mipmap.not_access);
            textView.setText("未授权！");
            textView.setTextColor(Color.RED);
        }

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

}
