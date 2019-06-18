package com.lab.web.activity.natives;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lab.web.common.Constants;
import com.lab.web.view.DrawShowBottomView;

import lab.tonglu.com.hybriddemo.R;

public class TestActivity extends AppCompatActivity implements DrawShowBottomView.OnScrollTouchListener,
DrawShowBottomView.ArwShowFlag{
    private ImageView iv_divide,iv_level1,iv_level2,iv_level3,iv_level4,iv_level5,iv_divide1,iv_divide2l,iv_divide3l,iv_divide4l,iv_divide5,iv_divide2r,iv_divide3r,iv_divide4r;
    private DrawShowBottomView group_dsv;
    private Button btn_level;
    private Context context;
    private DrawShowBottomView.ArwShowFlag arwShowFlag;
    private DrawShowBottomView drawShowBottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_test);
        initView();
    }
    private void initView() {
        WindowManager wm = (WindowManager)this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        group_dsv= (DrawShowBottomView) findViewById(R.id.group_dsv);
        btn_level= (Button) findViewById(R.id.btn_level);
        iv_level1= (ImageView) findViewById(R.id.iv_level1);
        iv_level2= (ImageView) findViewById(R.id.iv_level2);
        iv_level3= (ImageView) findViewById(R.id.iv_level3);
        iv_level4= (ImageView) findViewById(R.id.iv_level4);
        iv_level5= (ImageView) findViewById(R.id.iv_level5);

        iv_divide1= (ImageView) findViewById(R.id.iv_divide1);
        iv_divide2l= (ImageView) findViewById(R.id.iv_divide2l);
        iv_divide3l= (ImageView) findViewById(R.id.iv_divide3l);
        iv_divide4l= (ImageView) findViewById(R.id.iv_divide4l);
        iv_divide5= (ImageView) findViewById(R.id.iv_divide5);
        iv_divide2r= (ImageView) findViewById(R.id.iv_divide2r);
        iv_divide3r= (ImageView) findViewById(R.id.iv_divide3r);
        iv_divide4r= (ImageView) findViewById(R.id.iv_divide4r);

//        iv_level1.setOnClickListener(this);
//        iv_level2.setOnClickListener(this);
//        iv_level3.setOnClickListener(this);
//        iv_level4.setOnClickListener(this);
//        iv_level5.setOnClickListener(this);
//       滑动监听接口
        group_dsv.setOnScrollTouchListener(this);
        group_dsv.setOnArwShowFlag(this);
        group_dsv.setPostion(1);//通过方法向ViewGroup中传入当前用户等级location实现进入页面时候的定位

    }
    @Override
    public void scrollTouch() {
//        Toast.makeText(this,"实现滑动监听接口 i",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void pos(int pos) {//获取ViewGroup中通过接口传过来的滑动后的等级，做图片和连接线的变换处理
        switch (pos){
            case 1:
                btn_level.setText(String.valueOf(pos));
                iv_level1.setImageResource(R.drawable.levelbutton1);
                iv_level2.setImageResource(R.drawable.huiyuan02);
                iv_level3.setImageResource(R.drawable.huiyuan03);
                iv_level4.setImageResource(R.drawable.huiyuan04);
                iv_level5.setImageResource(R.drawable.huiyuan05);
                clearDivie();
                break;
            case 2:
                btn_level.setText(String.valueOf(pos));
                iv_level2.setImageResource(R.drawable.levelbutton2);
                iv_level1.setImageResource(R.drawable.huiyuan01);
                iv_level3.setImageResource(R.drawable.huiyuan03);
                iv_level4.setImageResource(R.drawable.huiyuan04);
                iv_level5.setImageResource(R.drawable.huiyuan05);

                clearDivie();
                iv_divide1.setImageResource(R.drawable.red5line);
                iv_divide2l.setImageResource(R.drawable.red5line);
                break;
            case 3:
                btn_level.setText(String.valueOf(pos));
                iv_level3.setImageResource(R.drawable.levelbutton3);
                iv_level2.setImageResource(R.drawable.huiyuan02);
                iv_level1.setImageResource(R.drawable.huiyuan01);
                iv_level4.setImageResource(R.drawable.huiyuan04);
                iv_level5.setImageResource(R.drawable.huiyuan05);

                clearDivie();
                iv_divide1.setImageResource(R.drawable.red5line);
                iv_divide2l.setImageResource(R.drawable.red5line);
                iv_divide2r.setImageResource(R.drawable.red5line);
                iv_divide3l.setImageResource(R.drawable.red5line);

                break;
            case 4:
                iv_level4.setImageResource(R.drawable.levelbutton4);
                iv_level2.setImageResource(R.drawable.huiyuan02);
                iv_level3.setImageResource(R.drawable.huiyuan03);
                iv_level1.setImageResource(R.drawable.huiyuan01);
                iv_level5.setImageResource(R.drawable.huiyuan05);
                btn_level.setText(String.valueOf(pos));

                clearDivie();
                iv_divide1.setImageResource(R.drawable.red5line);
                iv_divide2l.setImageResource(R.drawable.red5line);
                iv_divide3l.setImageResource(R.drawable.red5line);
                iv_divide2r.setImageResource(R.drawable.red5line);
                iv_divide3r.setImageResource(R.drawable.red5line);
                iv_divide4l.setImageResource(R.drawable.red5line);
                break;
            case 5:
                iv_level5.setImageResource(R.drawable.levelbutton5);
                iv_level2.setImageResource(R.drawable.huiyuan02);
                iv_level3.setImageResource(R.drawable.huiyuan03);
                iv_level4.setImageResource(R.drawable.huiyuan04);
                iv_level1.setImageResource(R.drawable.huiyuan05);
                btn_level.setText(String.valueOf(pos));

                iv_divide1.setImageResource(R.drawable.red5line);
                iv_divide2l.setImageResource(R.drawable.red5line);
                iv_divide3l.setImageResource(R.drawable.red5line);
                iv_divide4l.setImageResource(R.drawable.red5line);
                iv_divide5.setImageResource(R.drawable.red5line);
                iv_divide2r.setImageResource(R.drawable.red5line);
                iv_divide3r.setImageResource(R.drawable.red5line);
                iv_divide4r.setImageResource(R.drawable.red5line);
                break;
        }
    }
    private void clearDivie(){
        iv_divide1.setImageResource(R.drawable.write5line);
        iv_divide2l.setImageResource(R.drawable.write5line);
        iv_divide3l.setImageResource(R.drawable.write5line);
        iv_divide4l.setImageResource(R.drawable.write5line);
        iv_divide5.setImageResource(R.drawable.write5line);
        iv_divide2r.setImageResource(R.drawable.write5line);
        iv_divide3r.setImageResource(R.drawable.write5line);
        iv_divide4r.setImageResource(R.drawable.write5line);
    }
}
