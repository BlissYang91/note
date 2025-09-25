import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BatchFileDownloader {
    // 要下载的文件URL列表
    private static final List<String> DOWNLOAD_URLS = Arrays.asList(
        "https://example.com/file1.txt",
        "https://example.com/file2.jpg",
        "https://example.com/file3.pdf",
        "https://example.com/file4.zip"
    );
    
    // 下载文件保存目录
    private static final String SAVE_DIRECTORY = "downloads";
    
    // 用于管理所有订阅，方便取消
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static void main(String[] args) {
        BatchFileDownloader downloader = new BatchFileDownloader();
        downloader.startBatchDownload();
        
        // 等待所有下载任务完成（实际应用中可根据需求调整）
        try {
            // 最多等待10分钟
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            downloader.dispose();
        }
    }

    /**
     * 开始批量下载任务
     */
    public void startBatchDownload() {
        // 创建保存目录
        createDirectoryIfNotExists(SAVE_DIRECTORY);
        
        // 循环遍历URL列表，创建下载任务
        Observable.fromIterable(DOWNLOAD_URLS)
            .concatMap(url -> downloadFile(url)
                .subscribeOn(Schedulers.io())
                // 失败时重试2次，每次间隔1秒
                .retryWhen(errors -> errors
                    .zipWith(Observable.range(1, 3), (error, retryCount) -> retryCount)
                    .flatMap(retryCount -> {
                        System.out.println("下载失败，准备第 " + retryCount + " 次重试: " + url);
                        return Observable.timer(retryCount, TimeUnit.SECONDS);
                    })
                )
                // 单个任务失败不影响其他任务
                .onErrorResumeNext(error -> {
                    System.err.println("文件下载最终失败: " + url + ", 错误: " + error.getMessage());
                    return Observable.empty();
                })
            )
            .subscribe(new Observer<DownloadResult>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                    System.out.println("开始批量下载任务...");
                }

                @Override
                public void onNext(DownloadResult result) {
                    System.out.println("文件下载成功: " + result.getFileName() + 
                                       ", 大小: " + result.getFileSize() + " bytes");
                }

                @Override
                public void onError(Throwable e) {
                    System.err.println("批量下载发生错误: " + e.getMessage());
                }

                @Override
                public void onComplete() {
                    System.out.println("所有下载任务已完成!");
                }
            });
    }

    /**
     * 下载单个文件
     * @param fileUrl 文件URL
     * @return 包含下载结果的Observable
     */
    private Observable<DownloadResult> downloadFile(String fileUrl) {
        return Observable.create(emitter -> {
            // 获取文件名
            String fileName = getFileNameFromUrl(fileUrl);
            String savePath = SAVE_DIRECTORY + File.separator + fileName;
            
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            
            try {
                URL url = new URL(fileUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000); // 10秒连接超时
                connection.setReadTimeout(30000);    // 30秒读取超时
                
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    emitter.onError(new RuntimeException("服务器响应错误: " + responseCode));
                    return;
                }
                
                // 获取文件大小
                long fileSize = connection.getContentLengthLong();
                inputStream = connection.getInputStream();
                outputStream = new FileOutputStream(savePath);
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalRead = 0;
                
                // 读取并写入文件
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    // 检查是否已取消订阅
                    if (emitter.isDisposed()) {
                        cleanupFile(savePath); // 清理未完成的文件
                        return;
                    }
                    
                    outputStream.write(buffer, 0, bytesRead);
                    totalRead += bytesRead;
                    
                    // 计算并发布进度（每10%发布一次）
                    if (fileSize > 0) {
                        int progress = (int) ((totalRead * 100) / fileSize);
                        if (progress % 10 == 0) {
                            System.out.println("下载中: " + fileName + " " + progress + "%");
                        }
                    }
                }
                
                // 验证文件大小
                if (fileSize > 0 && totalRead != fileSize) {
                    cleanupFile(savePath);
                    emitter.onError(new RuntimeException("文件不完整，预期大小: " + fileSize + ", 实际大小: " + totalRead));
                    return;
                }
                
                // 下载成功
                emitter.onNext(new DownloadResult(fileName, savePath, totalRead));
                emitter.onComplete();
                
            } catch (Exception e) {
                if (!emitter.isDisposed()) {
                    cleanupFile(savePath);
                    emitter.onError(e);
                }
            } finally {
                // 关闭资源
                try {
                    if (inputStream != null) inputStream.close();
                    if (outputStream != null) outputStream.close();
                    if (connection != null) connection.disconnect();
                } catch (Exception e) {
                    // 忽略关闭资源的异常
                }
            }
        });
    }

    /**
     * 从URL中提取文件名
     */
    private String getFileNameFromUrl(String url) {
        if (url == null || url.isEmpty()) return "unknown_file";
        
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < url.length() - 1) {
            return url.substring(lastSlashIndex + 1);
        }
        
        return "unknown_file_" + System.currentTimeMillis();
    }

    /**
     * 创建目录（如果不存在）
     */
    private void createDirectoryIfNotExists(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 清理未完成的文件
     */
    private void cleanupFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * 取消所有下载任务
     */
    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            System.out.println("所有下载任务已取消");
        }
    }

    /**
     * 下载结果实体类
     */
    private static class DownloadResult {
        private final String fileName;
        private final String savePath;
        private final long fileSize;

        public DownloadResult(String fileName, String savePath, long fileSize) {
            this.fileName = fileName;
            this.savePath = savePath;
            this.fileSize = fileSize;
        }

        public String getFileName() {
            return fileName;
        }

        public String getSavePath() {
            return savePath;
        }

        public long getFileSize() {
            return fileSize;
        }
    }
}
    