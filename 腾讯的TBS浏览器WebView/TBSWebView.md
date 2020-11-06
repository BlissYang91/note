## 自定义webview

```


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import com.beans.base.R;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @Author yangtianfu
 * @CreateTime 2020/4/15 19:52
 * @Describe 腾讯的TBS浏览器WebView
 */
public class TBSWebView extends WebView {

    private ProgressBar progressbar;  //进度条
    private int progressHeight = 5;  //进度条的高度，默认10px
    private Context mContext;
    private boolean isShowProgressbar = true;//是否显示进度条

    public TBSWebView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public TBSWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        startTime = System.currentTimeMillis();

        //开启js脚本支持
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);

        //创建进度条
        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        //设置加载进度条的高度
        progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(LayoutParams.MATCH_PARENT, progressHeight, 0, 0));

        Drawable drawable = context.getResources().getDrawable(R.drawable.base_webview_progress);
        progressbar.setProgressDrawable(drawable);

        //添加进度到WebView
        addView(progressbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //适配手机大小
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setGeolocationEnabled(true);
        String dir = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setGeolocationDatabasePath(dir);
        // enable Web Storage: localStorage, sessionStorage
        settings.setDomStorageEnabled(true);
        // 设置 缓存模式
//        s.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置 缓存模式
        settings.setAppCacheMaxSize(1024 * 1024 * 15);// 设置缓冲大小，我设的是15M
        String cacheDirPath = context.getFilesDir().getAbsolutePath() + "cache/";
        settings.setAppCachePath(cacheDirPath);
        // 开启 Application Caches 功能
        settings.setAppCacheEnabled(true);
        // 设置 Application Caches 缓存目录
        settings.setAppCachePath(cacheDirPath);
        settings.setAllowFileAccess(true);
        // 触摸焦点起作用
        requestFocus();
        setWebChromeClient(new WVChromeClient());
        setWebViewClient(new WVClient());

        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public void loadUrl(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        //设置cookie
//        SystemUtils.synCookies(AppApplication.getInstance(), s);
        super.loadUrl(s);
    }

    /**
     * 是否显示进度条===默认显示
     * @param flagBar
     */
    public void isShowProgressBar(boolean flagBar) {
        this.isShowProgressbar = flagBar;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canGoBack()) {
                goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //进度显示
    private class WVChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (isShowProgressbar) {
                if (newProgress == 100) {
                    progressbar.setVisibility(GONE);
                } else {
                    if (progressbar.getVisibility() == GONE)
                        progressbar.setVisibility(VISIBLE);
                    progressbar.setProgress(newProgress);
                }
            } else {
                progressbar.setVisibility(GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            if (mListener != null) {
                mListener.onReceivedTitle(s);
            }

        }
    }

    private long startTime;

    private class WVClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            if (mListener != null) {
                mListener.onStart();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            LogUtil.e("shouldOverrideUrlLoading===" + url);
            //这里需要写成false，true会导致h5中的二级界面可能无法跳转
            return false;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
//            synCookies(webView.getContext(), webResourceRequest.getUrl().toString());
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            //设置接收所有证书
            sslErrorHandler.proceed();
        }

        @Override
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
            if (mListener != null) {
                mListener.onLoadError();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
//            LogUtil.e("onPageFinished===" + url);
//            ToastUtils.getInstance().showToast("耗时=" + (System.currentTimeMillis() - startTime));
            progressbar.setVisibility(GONE);
            if (mListener != null) {
                mListener.onPageFinish(view);
            }
            super.onPageFinished(view, url);

        }

    }

    private OnWebViewListener mListener;

    public void setOnWebViewListener(OnWebViewListener listener) {
        this.mListener = listener;
    }

    //进度回调接口
    public interface OnWebViewListener {
        void onStart();

        void onLoadError();

        void onPageFinish(WebView view);

        void onReceivedTitle(String title);
    }
}

```
## 进度条
- [x] base_webview_progress
```
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@android:id/progress">
        <!--需要这样设置颜色，直接通过drawable设置颜色无效果-->
        <clip>
            <shape>
                <solid android:color="#FD674F"/>
            </shape>

        </clip>
    </item>
    <item android:id="@android:id/background">
        <clip>
            <shape>
                <solid android:color="#F6F6F6"/>
            </shape>

        </clip>
    </item>
</layer-list>
```

## weiview打开本地PDF文件

- [x] 权限定名

```
// PDF 文件保存路径
val PDF_PATH: String = android.os.Environment.getExternalStorageDirectory().getPath().toString() + "/wey/"
val PDF_PATH_NAME: String = "${PDF_PATH}direction.pdf"
```


```
<!--   webView打开PDF     -->
        <RelativeLayout
            android:id="@+id/rl_pdf"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <com.beans.base.widget.TBSWebView
                android:id="@+id/webView"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
            </com.beans.base.widget.TBSWebView>
        </RelativeLayout>

```


```
  var tbsReaderView:TbsReaderView? = null
  
   var  readerCallback:TbsReaderView.ReaderCallback = object : TbsReaderView.ReaderCallback {
        override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {

        }
    }
    
  tbsReaderView = TbsReaderView(this,readerCallback)
        mBinding.rlPdf.addView(tbsReaderView,RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT))
```


```
   /**
     * 打开本地PDF文件
     */
    private fun openPDF() {
        mBinding.llInfo.visibility = View.GONE
        mBinding.rlPdf.visibility = View.VISIBLE
        var bundle = Bundle()
        bundle.putString("filePath", PDF_PATH_NAME)
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().path)
        var result = tbsReaderView!!.preOpen(parseFormat(parseName()),false)
        if (result){
            tbsReaderView!!.openFile(bundle)
        }else{
            logE("打开PDF失败")
        }
    }
    private fun parseFormat( fileName:String):String{
       return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    private fun parseName():String{
      var fileName:String
        fileName = PDF_PATH_NAME.substring(PDF_PATH_NAME.lastIndexOf("/")+1)
        return  fileName
    }
```


