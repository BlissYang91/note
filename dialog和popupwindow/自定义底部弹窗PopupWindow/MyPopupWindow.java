package cn.kudesoft.daka.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import cn.kudesoft.daka.R;

/**
 * @author YangTianFu
 * @date 2019/6/18  14:14
 * @description 自定义底部弹出框
 */
public class MyPopupWindow extends PopupWindow {
    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private View mMenuView;
    private DismissWindowListener mListener;

    public MyPopupWindow(Context context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_select_layout, null);
        btn_take_photo = (Button) mMenuView.findViewById(R.id.btn_take_photo);
        btn_pick_photo = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //销毁弹出框
                if(mListener != null) {
                    mListener.dismissEvent();
                }
                dismiss();
            }
        });
        //设置按钮监听
        btn_pick_photo.setOnClickListener(itemsOnClick);
        btn_take_photo.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.select_pic_pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        if(mListener != null) {
                            mListener.dismissEvent();
                        }
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public interface DismissWindowListener {
        void dismissEvent();
    }

    public void setDismissWindowListener(DismissWindowListener listener) {
        mListener = listener;
    }
}
/****************************************使用********************************************* */

// private MyPopupWindow mPopupWindow;
//     /**
//      * 为弹出窗口实现监听类
//      */
//     private View.OnClickListener itemsOnClick = new View.OnClickListener() {

//         @Override
//         public void onClick(View v) {
//             mPopupWindow.dismiss();
//             //从相册选择 解决7.0系统打开sd卡找不到文件的问题
// //                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
// //                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
// //                        StrictMode.setVmPolicy(builder.build());
// //                    }
//             switch (v.getId()) {
//                 case R.id.btn_take_photo:
//                     Log.e(TAG, "onClick: 拍照");
//                     break;
//                 case R.id.btn_pick_photo:
//                     Log.e(TAG, "onClick: 选择图片");
//                     break;
//                 default:
//                     break;
//             }
//         }

//     };

//     mPopupWindow = new MyPopupWindow(this, itemsOnClick);
//     mPopupWindow.setDismissWindowListener(new MyPopupWindow.DismissWindowListener() {

//         @Override
//         public void dismissEvent() {
//             Log.e(TAG, "MyPopupWindow dismissEvent: " );
//         }
//     });
//     //设置layout在PopupWindow中显示的位置(根布局)
//     mPopupWindow.showAtLocation(HomeActivity.this.findViewById(R.id.ll_home), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);