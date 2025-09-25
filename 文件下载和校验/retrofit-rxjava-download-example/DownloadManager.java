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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownloadManager {
    private static final String TAG = "DownloadManager";
    private static final String BASE_URL = "https://api.example.com/";
    private ApiService apiService;
    private Disposable disposable;

    public DownloadManager() {
        // 初始化Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    /**
     * 开始完整的下载流程：获取下载信息 -> 下载文件 -> 校验文件
     */
    public void startDownload(DownloadListener listener) {
        // 获取下载信息
        disposable = apiService.getDownloadInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(downloadInfo -> {
                    // 下载文件
                    listener.onDownloadStart(downloadInfo);
                    return downloadFile(downloadInfo, listener);
                })
                .flatMap(downloadInfo -> {
                    // 校验文件完整性
                    return checkFileIntegrity(downloadInfo, listener);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        downloadInfo -> {
                            // 下载和校验成功
                            listener.onDownloadComplete(downloadInfo);
                        },
                        error -> {
                            // 发生错误
                            Log.e(TAG, "Download error: " + error.getMessage());
                            listener.onDownloadFailed(error.getMessage());
                        }
                );
    }

    /**
     * 下载文件到本地存储
     */
    private Observable<DownloadInfo> downloadFile(DownloadInfo info, DownloadListener listener) {
        return apiService.downloadFile(info.getFileUrl())
                .map(responseBody -> {
                    saveFileToStorage(responseBody, info, listener);
                    return info;
                });
    }

    /**
     * 将文件保存到本地存储
     */
    private void saveFileToStorage(ResponseBody body, DownloadInfo info, DownloadListener listener) throws IOException {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            byte[] fileReader = new byte[4096];
            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;

            // 获取下载目录
            File downloadDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "MyAppDownloads");
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }

            // 创建文件
            File file = new File(downloadDir, info.getFileName());
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(file);

            // 写入文件并更新进度
            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }

                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += read;
                
                // 计算进度并回调
                int progress = (int) ((fileSizeDownloaded * 100) / fileSize);
                listener.onDownloadProgress(progress, fileSizeDownloaded, fileSize);
            }

            outputStream.flush();
            info.setFileSize(fileSize); // 更新实际文件大小
            Log.d(TAG, "File saved to: " + file.getAbsolutePath());

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    /**
     * 校验文件完整性（MD5校验）
     */
    private Observable<DownloadInfo> checkFileIntegrity(DownloadInfo info, DownloadListener listener) {
        return Observable.create(emitter -> {
            try {
                // 获取文件路径
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), "MyAppDownloads/" + info.getFileName());
                
                // 计算文件MD5
                String fileMd5 = calculateFileMd5(file);
                Log.d(TAG, "Calculated MD5: " + fileMd5);
                Log.d(TAG, "Expected MD5: " + info.getFileMd5());

                // 校验MD5
                if (fileMd5.equalsIgnoreCase(info.getFileMd5())) {
                    listener.onFileVerified(true);
                    emitter.onNext(info);
                    emitter.onComplete();
                } else {
                    // MD5不匹配，删除文件
                    if (file.exists()) {
                        file.delete();
                    }
                    listener.onFileVerified(false);
                    emitter.onError(new Exception("File integrity check failed: MD5 mismatch"));
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    /**
     * 计算文件的MD5值
     */
    private String calculateFileMd5(File file) throws NoSuchAlgorithmException, IOException {
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
        
        is.close();
        return sb.toString();
    }

    /**
     * 取消下载
     */
    public void cancelDownload() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            Log.d(TAG, "Download cancelled");
        }
    }

    /**
     * 下载监听接口
     */
    public interface DownloadListener {
        void onDownloadStart(DownloadInfo info);
        void onDownloadProgress(int progress, long downloadedSize, long totalSize);
        void onFileVerified(boolean isVerified);
        void onDownloadComplete(DownloadInfo info);
        void onDownloadFailed(String errorMessage);
    }
}
