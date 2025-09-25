import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DownloadActivity extends AppCompatActivity implements DownloadManager.DownloadListener {
    private ProgressBar progressBar;
    private TextView statusTextView;
    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        progressBar = findViewById(R.id.progress_bar);
        statusTextView = findViewById(R.id.status_text);

        // 初始化下载管理器
        ApiService apiService = RetrofitClient.getApiService();
        downloadManager = new DownloadManager(apiService);
        downloadManager.setListener(this);

        // 开始下载
        startDownload();
    }

    private void startDownload() {
        statusTextView.setText("正在获取下载信息...");
        downloadManager.startDownload();
    }

    @Override
    public void onDownloadStart(String fileName, long fileSize) {
        runOnUiThread(() -> {
            statusTextView.setText("开始下载: " + fileName);
            progressBar.setProgress(0);
        });
    }

    @Override
    public void onDownloadProgress(int progress, long downloadedSize, long totalSize) {
        runOnUiThread(() -> {
            progressBar.setProgress(progress);
            statusTextView.setText(String.format("下载中: %d%% (%d/%d MB)", 
                    progress, 
                    downloadedSize / (1024 * 1024), 
                    totalSize / (1024 * 1024)));
        });
    }

    @Override
    public void onDownloadComplete(String filePath) {
        runOnUiThread(() -> {
            statusTextView.setText("下载完成: " + filePath);
            Toast.makeText(this, "文件已保存至: " + filePath, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onDownloadFailed(String errorMessage) {
        runOnUiThread(() -> {
            statusTextView.setText("下载失败: " + errorMessage);
            Toast.makeText(this, "下载失败: " + errorMessage, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onDownloadCancelled() {
        runOnUiThread(() -> {
            statusTextView.setText("下载已取消");
            Toast.makeText(this, "下载已取消", Toast.LENGTH_SHORT).show();
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
}
    