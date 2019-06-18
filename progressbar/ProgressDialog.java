package bang.lib.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import bang.lib.R;

/**
 * @autor YangTianFu
 * @Date 2019/4/19  9:36
 * @Description
 */
public class ProgressDialog {
    private Context context;
    private Dialog dialog;
    private Display display;

    public ProgressDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        dialog = new Dialog(context, R.style.AlertDialogStyle);
    }

    public ProgressDialog builder() {
        return this;
    }

    public ProgressDialog addView(View view) {
        dialog.setContentView(view);
        view.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    /**
     * 设置布局的比例
     * @param view
     * @param scale 0到1   0.3
     * @return
     */
    public ProgressDialog addView(View view, float scale) {
        dialog.setContentView(view);
        view.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * scale), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public void show() {
        dialog.show();
    }


    public ProgressDialog setCancelable(boolean flag) {
        dialog.setCancelable(flag);
        return this;
    }

    public void dissmis() {
        dialog.dismiss();
    }
}
