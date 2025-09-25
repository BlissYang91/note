import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DownloadManager {
    private final ApiService apiService;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DownloadListener listener;
    private int maxRetries = 3; // 最大重试次数
    private int retryCount = 0; // 当前重试次数

    public DownloadManager(ApiService apiService) {
        this.apiService = apiService;
    }

    public void startDownload() {
        // 获取下载地址
        Disposable disposable = apiService.getDownloadInfo()
                .subscribeOn(Schedulers.io())
                .flatMap(downloadInfo -> {
                    // 开始下载文件
                    if (listener != null) {
                        listener.onDownloadStart(downloadInfo.getFileName(), downloadInfo.getFileSize());
                    }
                    return downloadFile(downloadInfo.getUrl(), downloadInfo.getFileName(), 
                                       downloadInfo.getFileSize(), downloadInfo.getMd5());
                })
                // 关键：正确实现 retryWhen，返回 Observable<? extends Throwable>
                .retryWhen(throwableObservable -> throwableObservable
                        .flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                            // 检查是否达到最大重试次数
                            if (retryCount < maxRetries) {
                                retryCount++;
                                // 每次重试前延迟1秒
                                if (listener != null) {
                                    listener.onDownloadFailed("下载失败，正在重试 (" + retryCount + "/" + maxRetries + ")");
                                }
                                return Observable.timer(1, java.util.concurrent.TimeUnit.SECONDS);
                            }
                            // 达到最大重试次数，发送错误
                            return Observable.error(new Throwable("已达到最大重试次数，下载失败"));
                        }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filePath -> {
                            if (listener != null) {
                                listener.onDownloadComplete(filePath);
                            }
                        },
                        error -> {
                            if (listener != null) {
                                listener.onDownloadFailed(error.getMessage());
                            }
                        }
                );

        compositeDisposable.add(disposable);
    }

    private Observable<String> downloadFile(String url, String fileName, long fileSize, String expectedMd5) {
        // 实现文件下载逻辑
        return Observable.create(emitter -> {
            // 实际下载代码...
        });
    }

    public void cancelDownload() {
        compositeDisposable.clear();
        if (listener != null) {
            listener.onDownloadCancelled();
        }
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }

    public interface DownloadListener {
        void onDownloadStart(String fileName, long fileSize);
        void onDownloadProgress(int progress, long downloadedSize, long totalSize);
        void onDownloadComplete(String filePath);
        void onDownloadFailed(String errorMessage);
        void onDownloadCancelled();
    }
}
    