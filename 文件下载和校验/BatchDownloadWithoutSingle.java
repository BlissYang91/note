import io.reactivex.Observable;
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
import java.util.concurrent.atomic.AtomicInteger;

public class BatchDownloadWithoutSingle {

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
    // 计数器，用于跟踪已完成的下载任务
    private static final AtomicInteger completedCount = new AtomicInteger(0);
    // 总任务数
    private static final int TOTAL_TASKS = FILE_URLS.size();

    public static void main(String[] args) {
        // 创建下载目录
        new File(DOWNLOAD_DIR).mkdirs();
        
        System.out.println("开始批量下载... 共 " + TOTAL_TASKS + " 个文件");
        
        // 创建下载任务Observable
        Observable<String> downloadObservable = Observable.fromIterable(FILE_URLS)
            .flatMap(url -> createDownloadObservable(url)
                .retryWhen(errors -> 
                    errors.zipWith(Observable.range(1, MAX_RETRIES + 1), 
                        (error, attempt) -> {
                            if (attempt <= MAX_RETRIES) {
                                String fileName = new File(new URL(url).getPath()).getName();
                                System.out.println("准备重试下载 " + fileName + 
                                    " (第" + attempt + "次重试)");
                                return attempt;
                            }
                            throw new RuntimeException("超过最大重试次数", error);
                        })
                    .delay(RETRY_DELAY_SECONDS, TimeUnit.SECONDS)
                )
            )
            .subscribeOn(Schedulers.io());
        
        // 订阅下载结果，跟踪每个文件的下载状态
        Disposable disposable = downloadObservable
            .observeOn(Schedulers.single())
            .subscribe(
                fileName -> {
                    // 单个文件下载成功
                    System.out.println("文件下载成功: " + fileName);
                    checkAllCompleted();
                },
                error -> {
                    // 单个文件最终下载失败
                    System.err.println("文件下载失败: " + error.getMessage());
                    checkAllCompleted();
                }
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
    
    // 创建单个文件下载的Observable
    private static Observable<String> createDownloadObservable(String fileUrl) {
        return Observable.create(emitter -> {
            try {
                String fileName = downloadFile(fileUrl);
                emitter.onNext(fileName);  // 发射成功下载的文件名
                emitter.onComplete();      // 标记当前Observable完成
            } catch (Exception e) {
                emitter.onError(e);        // 发射错误
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
        
        return fileName;
    }
    
    // 检查所有任务是否都已完成
    private static void checkAllCompleted() {
        int current = completedCount.incrementAndGet();
        if (current == TOTAL_TASKS) {
            System.out.println("所有文件下载任务已处理完成!");
        }
    }
}
