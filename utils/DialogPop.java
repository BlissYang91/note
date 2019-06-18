
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bliss.yang.signingapplication.R;

public class DialogPop {

    private PopupWindow popupWindow;
    private Context context;

    public DialogPop(Context context) {
        this.context = context;
    }

    // 显示dialog
    public void showDialog(int layout) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_dialog, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);

        String message = "加载中";
        if (message == null || message.length() == 0) {
            view.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = view.findViewById(R.id.message);
            txt.setText(message);
        }

        ImageView imageView = view.findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();

        View rootview = LayoutInflater.from(context).inflate(layout, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    // 显示dialog
    public void showDialog(int layout, String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_dialog, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);

        if (message == null || message.length() == 0) {
            view.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = view.findViewById(R.id.message);
            txt.setText(message);
        }

        ImageView imageView = view.findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();

        View rootview = LayoutInflater.from(context).inflate(layout, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

}
