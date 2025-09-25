import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class DownloadManager {
    private static final String TAG = "DownloadManager";
    private final ApiService apiService;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DownloadListener listener;

    public DownloadManager(ApiService apiService) {
        this.apiService = apiService;
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }

    // 开始下载流程：获取下载地址 -> 下载文件 -> 校验文件
    public void startDownload() {
        Disposable disposable = apiService.getDownloadInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(downloadInfo -> {
                    // 回调通知开始下载
                    if (listener != null) {
                        Observable.just(downloadInfo)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(info -> listener.onDownloadStart(info.getFileName(), info.getFileSize()));
                    }
                    // 下载文件
                    return downloadFile(downloadInfo.getFileUrl(), 
                                       downloadInfo.getFileName(), 
                                       downloadInfo.getMd5Checksum());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            if (listener != null) {
                                if (result.isSuccess) {
                                    listener.onDownloadComplete(result.filePath);
                                } else {
                                    listener.onDownloadFailed("文件校验失败");
                                }
                            }
                        },
                        error -> {
                            Log.e(TAG, "下载失败: " + error.getMessage());
                            if (listener != null) {
                                listener.onDownloadFailed(error.getMessage());
                            }
                        }
                );

        compositeDisposable.add(disposable);
    }

    // 下载文件并返回文件信息
    private Observable<DownloadResult> downloadFile(String fileUrl, String fileName, String expectedMd5) {
        return apiService.downloadFile(fileUrl)
                .map(responseBody -> {
                    File file = saveFileToStorage(responseBody, fileName, expectedMd5);
                    boolean isVerified = verifyFileChecksum(file, expectedMd5);
                    return new DownloadResult(isVerified, file.getAbsolutePath());
                });
    }

    // 将文件保存到存储
    private File saveFileToStorage(ResponseBody body, String fileName, String expectedMd5) throws IOException {
        File downloadDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "MyDownloads");
        
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        File file = new File(downloadDir, fileName);
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            byte[] fileReader = new byte[4096];
            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;

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
                int progress = (int) ((fileSizeDownloaded * 100) / fileSize);
                if (listener != null) {
                    final int finalProgress = progress;
                    final long finalFileSizeDownloaded = fileSizeDownloaded;
                    Observable.just(0)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(ignored -> 
                                listener.onDownloadProgress(finalProgress, finalFileSizeDownloaded, fileSize));
                }
            }

            outputStream.flush();
            return file;
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
    private boolean verifyFileChecksum(File file, String expectedMd5) {
        if (expectedMd5 == null || expectedMd5.isEmpty()) {
            Log.w(TAG, "没有提供预期的MD5值，跳过校验");
            return true;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
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
            Log.e(TAG, "校验文件失败", e);
            return false;
        }
    }

    // 取消下载
    public void cancelDownload() {
        compositeDisposable.dispose();
        if (listener != null) {
            listener.onDownloadCancelled();
        }
    }

    // 下载结果数据类
    private static class DownloadResult {
        boolean isSuccess;
        String filePath;

        DownloadResult(boolean isSuccess, String filePath) {
            this.isSuccess = isSuccess;
            this.filePath = filePath;
        }
    }

    // 下载监听器接口
    public interface DownloadListener {
        void onDownloadStart(String fileName, long fileSize);
        void onDownloadProgress(int progress, long downloadedSize, long totalSize);
        void onDownloadComplete(String filePath);
        void onDownloadFailed(String errorMessage);
        void onDownloadCancelled();
    }
}
    