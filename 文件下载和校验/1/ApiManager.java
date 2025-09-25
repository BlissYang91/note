import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class ApiManager {
    private static final int MAX_RETRY_COUNT = 3; // 最大重试次数
    private static final int INITIAL_DELAY = 1; // 初始重试延迟（秒）
    private final ApiService apiService;

    public ApiManager() {
        this.apiService = RetrofitClient.getInstance();
    }

    /**
     * 获取用户信息（带重试逻辑）
     * @param userId 用户ID
     * @return 处理后的Observable
     */
    public Observable<User> getUserInfoWithRetry(String userId) {
        return apiService.getUserInfo(userId)
                .subscribeOn(Schedulers.io()) // 网络请求在IO线程
                .flatMap((Function<BaseResponse<User>, ObservableSource<User>>) response -> {
                    // 处理接口返回值
                    if (response.isSuccess()) {
                        // 业务成功：返回数据
                        return Observable.just(response.getData());
                    } else if (response.needRetry()) {
                        // 业务需要重试（如服务器繁忙）：抛出重试异常
                        return Observable.error(new RetryException(response.getCode(), response.getMessage()));
                    } else {
                        // 其他业务错误：直接抛出异常
                        return Observable.error(new BusinessException(response.getCode(), response.getMessage()));
                    }
                })
                .retryWhen(new RetryWithDelay(MAX_RETRY_COUNT, INITIAL_DELAY)); // 重试逻辑
    }

    /**
     * 重试逻辑实现：根据异常类型和重试次数决定是否重试
     */
    private static class RetryWithDelay implements Function<Observable<Throwable>, ObservableSource<?>> {
        private final int maxRetries;
        private final int initialDelay;
        private int retryCount;

        public RetryWithDelay(int maxRetries, int initialDelay) {
            this.maxRetries = maxRetries;
            this.initialDelay = initialDelay;
            this.retryCount = 0;
        }

        @Override
        public ObservableSource<?> apply(Observable<Throwable> throwableObservable) {
            return throwableObservable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                // 只对需要重试的异常进行重试（RetryException）
                if (throwable instanceof RetryException && retryCount < maxRetries) {
                    retryCount++;
                    // 指数退避策略：每次重试延迟时间翻倍
                    long delay = initialDelay * (long) Math.pow(2, retryCount - 1);
                    return Observable.timer(delay, TimeUnit.SECONDS);
                }
                // 不需要重试的异常：直接传递错误
                return Observable.error(throwable);
            });
        }
    }

    // 自定义异常：需要重试的业务异常
    public static class RetryException extends Exception {
        private final int code;

        public RetryException(int code, String message) {
            super(message);
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    // 自定义异常：普通业务异常（不需要重试）
    public static class BusinessException extends Exception {
        private final int code;

        public BusinessException(int code, String message) {
            super(message);
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
    