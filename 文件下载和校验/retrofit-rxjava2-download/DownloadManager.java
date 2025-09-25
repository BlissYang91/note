import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class DownloadManager {
    private static final String TAG = "DownloadManager";
    private final ApiService apiService;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DownloadListener listener;
    private boolean isDownloading = false;
    
    public DownloadManager(ApiService apiService) {
        this.apiService = apiService;
    }
    
    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }
    
    public void startDownload() {
        if (isDownloading) {
            notifyFailed("正在下载中，请稍后再试");
            return;
        }
        
        isDownloading = true;
        
        // 1. 获取下载信息
        Disposable disposable = apiService.getDownloadInfo()
                .subscribeOn(Schedulers.io())
                .flatMap((Function<DownloadInfoResponse, Observable<ResponseBody>>) downloadInfo -> {
                    // 2. 开始下载文件
                    if (listener != null) {
                        listener.onDownloadStart(downloadInfo.getFileName(), downloadInfo.getFileSize());
                    }
                    return apiService.downloadFile(downloadInfo.getFileUrl())
                            .map(responseBody -> {
                                // 3. 保存文件到本地
                                String filePath = saveFile(responseBody, downloadInfo.getFileName(), 
                                        downloadInfo.getFileSize());
                                
                                // 4. 校验文件完整性
                                boolean isVerified = verifyFileChecksum(filePath, downloadInfo.getMd5Checksum());
                                
                                if (!isVerified) {
                                    // 校验失败，删除文件
                                    new File(filePath).delete();
                                    throw new IOException("文件校验失败，可能已损坏");
                                }
                                
                                return filePath;
                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filePath -> {
                            isDownloading = false;
                            if (listener != null) {
                                listener.onDownloadComplete(filePath);
                            }
                        },
                        error -> {
                            isDownloading = false;
                            Log.e(TAG, "下载错误: " + error.getMessage());
                            if (listener != null) {
                                listener.onDownloadFailed(error.getMessage());
                            }
                        }
                );
        
        compositeDisposable.add(disposable);
    }
    
    // 保存文件到本地存储
    private String saveFile(ResponseBody body, String fileName, long totalSize) throws IOException {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        
        try {
            byte[] fileReader = new byte[4096];
            long fileSizeDownloaded = 0;
            
            // 获取下载目录
            File downloadDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "MyAppDownloads"
            );
            
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }
            
            File file = new File(downloadDir, fileName);
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(file);
            
            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                
                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += read;
                
                // 计算进度并回调
                int progress = (int) ((fileSizeDownloaded * 100) / totalSize);
                if (listener != null) {
                    listener.onDownloadProgress(progress, fileSizeDownloaded, totalSize);
                }
            }
            
            outputStream.flush();
            return file.getAbsolutePath();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
    
    // 校验文件MD5
    private boolean verifyFileChecksum(String filePath, String expectedMd5) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            File file = new File(filePath);
            InputStream is = new java.io.FileInputStream(file);
            byte[] buffer = new byte[8192];
            int read;
            
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            
            byte[] md5sum = digest.digest();
            StringBuilder sb = new StringBuilder();
            
            for (byte b : md5sum) {
                sb.append(String.format("%02x", b));
            }
            
            String actualMd5 = sb.toString();
            Log.d(TAG, "计算的MD5: " + actualMd5);
            Log.d(TAG, "预期的MD5: " + expectedMd5);
            
            return actualMd5.equalsIgnoreCase(expectedMd5);
        } catch (NoSuchAlgorithmException | IOException e) {
            Log.e(TAG, "校验文件失败: " + e.getMessage());
            return false;
        }
    }
    
    // 取消下载
    public void cancelDownload() {
        compositeDisposable.clear();
        isDownloading = false;
        if (listener != null) {
            listener.onDownloadCancelled();
        }
    }
    
    // 下载监听接口
    public interface DownloadListener {
        void onDownloadStart(String fileName, long fileSize);
        void onDownloadProgress(int progress, long downloadedSize, long totalSize);
        void onDownloadComplete(String filePath);
        void onDownloadFailed(String errorMessage);
        void onDownloadCancelled();
    }
}
    