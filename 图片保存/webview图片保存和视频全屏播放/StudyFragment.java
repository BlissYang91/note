package com.thesis.course.minicourse.study;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolder;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.adapter.OnItemClickListener;
import com.lqr.audio.AudioPlayManager;
import com.lqr.audio.AudioRecordManager;
import com.lqr.audio.Constants;
import com.lqr.audio.IAudioPlayListener;
import com.lqr.audio.IAudioRecordListener;
import com.lqr.recyclerview.LQRRecyclerView;
import com.thesis.course.minicourse.R;
import com.thesis.course.minicourse.base.BaseFragment;
import com.thesis.course.minicourse.utils.LogUtil;
import com.thesis.course.minicourse.utils.ToastUtil;
import com.thesis.course.minicourse.web.JsApi;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionGen;
import wendu.dsbridge.DWebView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class StudyFragment extends BaseFragment implements UMShareListener {
  
    @BindView(R.id.webView)
    DWebView webView;

    @BindView(R.id.fl_video)
    FrameLayout fl_video;

    private String picUrl = "";
    private InputStream inputStream;
    private BufferedInputStream bufferedInputStream;
    private FileOutputStream fos;
    private WebView.HitTestResult hitTestResult;
    private File appDir=null;
    private String[] permissions;
    private Bitmap bi;

    private static final String TAG = "StudyFragment";

    public StudyFragment() {
    }

   
    @Override
    public void onResume() {
        super.onResume();
        initWebSetting();
        loadWeb();
    }

    private void loadWeb() {
        webView.loadUrl("https://image.baidu.com/");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 110){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "onRequestPermissionsResult: 取得存储权限" );
                if (bi != null){
                    saveToAlbum(bi);  
                }else {
                    ToastUtil.show(context, "图片错误");
                }
            } else {
                ToastUtil.show(context, "请开启存储权限");
            }
        }
    }

    private void initWebSetting() {
        if (webView!=null){
            webView.addJavascriptObject(new JsApi(), null);
        }else {
            return;
        }
        final WebSettings webSettings = webView.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setTextZoom(100);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//设置webview缓存模式
        webView.setVerticalScrollBarEnabled(false); // 取消Vertical ScrollBar显示
        webView.setHorizontalScrollBarEnabled(false); // 取消Horizontal ScrollBar显示
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //接受证书
                handler.proceed();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                hitTestResult=webView.getHitTestResult();
                // 如果是图片类型或者是带有图片链接的类型
                if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                        hitTestResult.getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE){
                    // 弹出保存图片的对话框
                    showBottomDialog();
                    return true;
                }
                return false;
            }
        });

    }

    private void showBottomDialog(){
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
                picUrl = hitTestResult.getExtra();//获取图片链接
//                            保存图片到相册
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        urlToBitMap(picUrl);
                    }
                }).start();
            }
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    /**
     * 将图片转为Bitmap
     * @param picUrl
     */
    private void urlToBitMap(String picUrl) {
        Bitmap bitmap=null;
        try {
            URL iconUrl=new URL(picUrl);
            URLConnection connection=iconUrl.openConnection();
            HttpURLConnection httpURLConnection= (HttpURLConnection) connection;
            int length = httpURLConnection.getContentLength();
            connection.connect();
            inputStream=connection.getInputStream();
            bufferedInputStream=new BufferedInputStream(inputStream,length);
            bitmap= BitmapFactory.decodeStream(bufferedInputStream);
            bufferedInputStream.close();
            inputStream.close();
            if (bitmap != null){
                bi = bitmap;
                saveToAlbum(bitmap);
            }
        } catch (Exception e) {
            Log.e(TAG, "urlToBitMap: 保存失败"+e.toString() );
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                }
            });
            e.printStackTrace();
        }
    }

    /**
     * 保存到相册
     * @param bitmap
     */
    private void saveToAlbum(Bitmap bitmap) {
        permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            手机系统6.0（23）以上动态申请权限
            int i=ContextCompat.checkSelfPermission(context,permissions[0]);
            if (i!=PackageManager.PERMISSION_GRANTED){
//                用户未授权，提醒授权
                ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},110);
            }else {
                appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Pictures");
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }
            }
        }else {
//            系统23以下不需要动态授权
            appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Pictures");
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
        }
        String[] str = picUrl.split("/");
        String fileName = str[str.length - 1];
        if (appDir != null){
            File file = new File(appDir, fileName);
            try {
                fos = new FileOutputStream(file);
                //解决7.0系统打开sd卡找不到文件的问题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                onSaveSuccess(file);
            } catch (final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "保存失败"+e.toString(), Toast.LENGTH_SHORT).show();
                        LogUtil.e("保存失败:",e.toString());
                    }
                });
                e.printStackTrace();
            }
        }else {
            Toast.makeText(context, "授权失败！", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 保存图片成功
     * @param file
     */
    private void onSaveSuccess(final File file) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                // 最后通知系统更新相册
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private class MyWebChromeClient extends WebChromeClient {
        private CustomViewCallback mCustomViewCallback;
        //  横屏时，显示视频的view
        private View mCustomView;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            mCustomViewCallback = callback;
            fl_video.addView(mCustomView);
            webView.setVisibility(View.GONE);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        @Override
        public void onHideCustomView() {
            webView.setVisibility(View.VISIBLE);
            super.onHideCustomView();
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            fl_video.removeView(mCustomView);
            mCustomView = null;
            try {
                if (mCustomViewCallback != null){
                    mCustomViewCallback.onCustomViewHidden();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
            webView.scrollBy(0,-200);
            EventBus.getDefault().post("onHideCustomView");
            super.onHideCustomView();
        }
    }

}
