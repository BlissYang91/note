import android.Manifest;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements DownloadManager.DownloadListener {
    private ProgressBar progressBar;
    private TextView statusText;
    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        statusText = findViewById(R.id.status_text);
        
        // 初始化下载管理器
        downloadManager = new DownloadManager();
        
        // 请求存储权限并开始下载
        MainActivityPermissionsDispatcher.startDownloadWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void startDownload() {
        statusText.setText("开始获取下载信息...");
        downloadManager.startDownload(this);
    }

    @Override
    public void onDownloadStart(DownloadInfo info) {
        runOnUiThread(() -> {
            statusText.setText("开始下载: " + info.getFileName());
            progressBar.setProgress(0);
        });
    }

    @Override
    public void onDownloadProgress(int progress, long downloadedSize, long totalSize) {
        runOnUiThread(() -> {
            progressBar.setProgress(progress);
            statusText.setText(String.format("下载中: %d%%", progress));
        });
    }

    @Override
    public void onFileVerified(boolean isVerified) {
        runOnUiThread(() -> {
            if (isVerified) {
                statusText.setText("文件校验成功");
            } else {
                statusText.setText("文件校验失败");
            }
        });
    }

    @Override
    public void onDownloadComplete(DownloadInfo info) {
        runOnUiThread(() -> {
            statusText.setText("下载完成: " + info.getFileName());
            Toast.makeText(this, "文件已保存到下载目录", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onDownloadFailed(String errorMessage) {
        runOnUiThread(() -> {
            statusText.setText("下载失败: " + errorMessage);
            Toast.makeText(this, "下载失败: " + errorMessage, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 页面销毁时取消下载
        if (downloadManager != null) {
            downloadManager.cancelDownload();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 转发权限结果
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
