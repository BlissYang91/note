
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201223104427552.png#pic_center)

## 布局文件

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <LinearLayout
        android:id="@+id/ll_code"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:layout_marginRight="7dp">
            <TextView
                android:id="@+id/tv_code1"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textColor="#2D2D2D"
                android:textSize="24sp"
                android:background="@null"
                android:gravity="center"/>
            <View
                android:id="@+id/v1"
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#F45701" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:layout_marginRight="7dp"
            android:layout_marginLeft="7dp">
            <TextView
                android:id="@+id/tv_code2"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textColor="#2D2D2D"
                android:textSize="24sp"
                android:background="@null"
                android:gravity="center"/>
            <View
                android:id="@+id/v2"
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#999999" />
        </LinearLayout>
        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:layout_marginRight="7dp"
            android:layout_marginLeft="7dp">
            <TextView
                android:id="@+id/tv_code3"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textColor="#2D2D2D"
                android:textSize="24sp"
                android:background="@null"
                android:gravity="center"/>
            <View
                android:id="@+id/v3"
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#999999" />
        </LinearLayout>
        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:layout_marginLeft="7dp">
            <TextView
                android:id="@+id/tv_code4"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textColor="#2D2D2D"
                android:background="@null"
                android:textSize="24sp"
                android:gravity="center"/>
            <View
                android:id="@+id/v4"
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#999999" />
        </LinearLayout>
        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:layout_marginLeft="7dp">
            <TextView
                android:id="@+id/tv_code5"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textColor="#2D2D2D"
                android:background="@null"
                android:textSize="24sp"
                android:gravity="center"/>
            <View
                android:id="@+id/v5"
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#999999" />
        </LinearLayout>
        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:layout_marginLeft="7dp">
            <TextView
                android:id="@+id/tv_code6"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textColor="#2D2D2D"
                android:background="@null"
                android:textSize="24sp"
                android:gravity="center"/>
            <View
                android:id="@+id/v6"
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#999999" />
        </LinearLayout>
    </LinearLayout>

    <EditText
        android:id="@+id/et_code"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignTop="@+id/ll_code"
        android:layout_alignBottom="@+id/ll_code"
        android:background="@android:color/transparent"
        android:textColor="@android:color/transparent"
        android:cursorVisible="false"
        android:inputType="text"/>
</RelativeLayout>
```
## 自定义view

```
package com.merge.car.widget;


import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.merge.car.R;

import java.util.ArrayList;
import java.util.List;

/**
* Author: yangtianfu
* Date: 2020/12/22 13:58
* Describe: 验证码输入
*/
public class PhoneCode extends RelativeLayout {
    private Context context;
    private TextView tv_code1;
    private TextView tv_code2;
    private TextView tv_code3;
    private TextView tv_code4;
    private TextView tv_code5;
    private TextView tv_code6;
    private View v1;
    private View v2;
    private View v3;
    private View v4;
    private View v5;
    private View v6;
    private EditText et_code;
    public List<String> codes = new ArrayList<>();
    private InputMethodManager imm;

    public PhoneCode(Context context) {
        super(context);
        this.context = context;
        loadView();
    }

    public PhoneCode(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        loadView();
    }

    private void loadView(){
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.ve_phone_code, this);
        initView(view);
        initEvent();
    }

    private void initView(View view){
        tv_code1 = view.findViewById(R.id.tv_code1);
        tv_code2 = view.findViewById(R.id.tv_code2);
        tv_code3 = view.findViewById(R.id.tv_code3);
        tv_code4 = view.findViewById(R.id.tv_code4);
        tv_code5 = view.findViewById(R.id.tv_code5);
        tv_code6 = view.findViewById(R.id.tv_code6);
        et_code =  view.findViewById(R.id.et_code);
        v1 = view.findViewById(R.id.v1);
        v2 = view.findViewById(R.id.v2);
        v3 = view.findViewById(R.id.v3);
        v4 = view.findViewById(R.id.v4);
        v5 = view.findViewById(R.id.v5);
        v6 = view.findViewById(R.id.v6);
    }

    private void initEvent(){
        //验证码输入
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable != null && editable.length()>0) {
                    et_code.setText("");
                    if(codes.size() < 6){
                        codes.add(editable.toString());
                        showCode();
                    }
                }
            }
        });
        // 监听验证码删除按键
        et_code.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && codes.size()>0) {
                    codes.remove(codes.size()-1);
                    showCode();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 显示输入的验证码
     */
    public void showCode(){
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        String code5 = "";
        String code6 = "";
        if(codes.size()>=1){
            code1 = codes.get(0);
        }
        if(codes.size()>=2){
            code2 = codes.get(1);
        }
        if(codes.size()>=3){
            code3 = codes.get(2);
        }
        if(codes.size()>=4){
            code4 = codes.get(3);
        }
        if(codes.size()>=5){
            code5 = codes.get(4);
        }
        if(codes.size()>=6){
            code6 = codes.get(5);
        }
        tv_code1.setText(code1);
        tv_code2.setText(code2);
        tv_code3.setText(code3);
        tv_code4.setText(code4);
        tv_code5.setText(code5);
        tv_code6.setText(code6);

        setColor();//设置高亮颜色
        callBack();//回调
    }

    /**
     * 设置高亮颜色
     */
    private void setColor(){
        int color_default = Color.parseColor("#333333");
        int color_focus = Color.parseColor("#F45701");
        v1.setBackgroundColor(color_default);
        v2.setBackgroundColor(color_default);
        v3.setBackgroundColor(color_default);
        v4.setBackgroundColor(color_default);
        v5.setBackgroundColor(color_default);
        v6.setBackgroundColor(color_default);
        if(codes.size()==0){
            v1.setBackgroundColor(color_focus);
            et_code.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if(codes.size()==1){
            v2.setBackgroundColor(color_focus);
            et_code.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if(codes.size()==2){
            v3.setBackgroundColor(color_focus);
            et_code.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if(codes.size()==3){
            v4.setBackgroundColor(color_focus);
            et_code.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if(codes.size()==4){
            v5.setBackgroundColor(color_focus);
            et_code.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if(codes.size()==5){
            v6.setBackgroundColor(color_focus);
            et_code.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    /**
     * 回调
     */
    private void callBack(){
        if(onInputListener==null){
            return;
        }
        if(codes.size()==6){
            onInputListener.onSuccess(getPhoneCode());
        }else{
            onInputListener.onInput(getPhoneCode());
        }
    }

    //定义回调
    public interface OnInputListener{
        void onSuccess(String code);
        void onInput(String code);
    }
    private OnInputListener onInputListener;
    public void setOnInputListener(OnInputListener onInputListener){
        this.onInputListener = onInputListener;
    }

    /**
     * 显示键盘
     */
    public void showSoftInput(){
        //显示软键盘
        if(imm!=null && et_code!=null) {
            et_code.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imm.showSoftInput(et_code, 0);
                }
            },200);
        }
    }

    /**
     * 获得手机号验证码
     * @return 验证码
     */
    public String getPhoneCode(){
        StringBuilder sb = new StringBuilder();
        for (String code : codes) {
            sb.append(code);
        }
        return sb.toString();
    }
}

```
## 输入监听

```
 phone_code.setOnInputListener(object : PhoneCode.OnInputListener {
            override fun onSuccess(code: String?) {
                idNumEnd = code.toString()
                btn_next.isEnabled = idNumEnd?.length == 6
            }

            override fun onInput(code: String?) {
                idNumEnd = code.toString()
                btn_next.isEnabled = code?.length == 6
            }
        })
```
