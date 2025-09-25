import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BatchDownloadWithSingle {

    // 要下载的文件URL列表
    private static final List<String> FILE_URLS = Arrays.asList(
        "https://example.com/file1.jpg",
        "https://example.com/file2.pdf",
        "https://example.com/file3.zip",
        "https://example.com/file4.txt"
    );
    
    // 下载目录
    private static final String DOWNLOAD_DIR = "downloads/";
    // 最大重试次数
    private static final int MAX_RETRIES = 3;
    // 重试间隔时间(秒)
    private static final int RETRY_DELAY_SECONDS = 2;

    public static void main(String[] args) {
        // 创建下载目录
        new File(DOWNLOAD_DIR).mkdirs();
        
        System.out.println("开始批量下载... 共 " + FILE_URLS.size() + " 个文件");
        
        // 创建批量下载的Single
        Single<List<String>> batchDownloadSingle = Observable.fromIterable(FILE_URLS)
            // 为每个URL创建下载任务，并转换为Single
            .map(url -> createDownloadSingle(url)
                .retryWhen(errors -> 
                    errors.zipWith(Observable.range(1, MAX_RETRIES + 1), 
                        (error, attempt) -> {
                            if (attempt <= MAX_RETRIES) {
                                String fileName = new File(new URL(url).getPath()).getName();
                                System.out.println("准备重试下载 " + fileName + 
                                    " (第" + attempt + "次重试)");
                                return attempt;
                            }
                            throw new RuntimeException("超过最大重试次数: " + url, error);
                        })
                    .delay(RETRY_DELAY_SECONDS, TimeUnit.SECONDS)
                )
                .blockingGet() // 等待当前文件下载完成
            )
            .subscribeOn(Schedulers.io())
            .toList(); // 将所有结果收集到List中，转换为Single
        
        // 订阅批量下载结果
        Disposable disposable = batchDownloadSingle
            .observeOn(Schedulers.single())
            .subscribe(
                // 所有文件下载完成（包括成功和失败）
                results -> {
                    System.out.println("所有文件下载任务已处理完成!");
                    System.out.println("成功下载的文件:");
                    results.forEach(fileName -> System.out.println("- " + fileName));
                },
                // 发生错误（如超过最大重试次数）
                error -> System.err.println("批量下载失败: " + error.getMessage())
            );
        
        // 防止主线程退出
        try {
            Thread.sleep(120000); // 等待120秒给下载和重试留出时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose(); // 清理资源
        }
    }
    
    // 创建单个文件下载的Single
    private static Single<String> createDownloadSingle(String fileUrl) {
        return Single.create(emitter -> {
            try {
                String fileName = downloadFile(fileUrl);
                emitter.onSuccess(fileName); // 下载成功，发射文件名
            } catch (Exception e) {
                emitter.onError(e); // 下载失败，发射错误
            }
        });
    }
    
    // 下载单个文件
    private static String downloadFile(String fileUrl) throws Exception {
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();
        
        // 获取文件名
        String fileName = new File(url.getPath()).getName();
        String savePath = DOWNLOAD_DIR + fileName;
        
        System.out.println("开始下载: " + fileName);
        
        // 读取输入流并写入文件
        try (InputStream in = connection.getInputStream();
             FileOutputStream out = new FileOutputStream(savePath)) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        
        System.out.println("下载完成: " + fileName);
        return fileName;
    }
}
