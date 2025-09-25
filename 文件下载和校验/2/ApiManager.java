import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * API管理类，处理网络请求和重试逻辑
 */
public class ApiManager {
    private static final int MAX_RETRY_COUNT = 3; // 最大重试次数
    private static final int RETRY_DELAY_SECONDS = 1; // 重试延迟时间(秒)
    
    private ApiService apiService;

    public ApiManager() {
        this.apiService = RetrofitClient.getApiService();
    }

    /**
     * 获取用户信息，带重试逻辑
     */
    public Observable<UserResponse> getUserInfoWithRetry(String userId) {
        return apiService.getUserInfo(userId)
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    private int retryCount = 0;
                    
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        // 处理重试逻辑
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                // 判断是否需要重试
                                if (throwable instanceof IOException || throwable instanceof HttpException) {
                                    // 网络错误或HTTP错误，进行重试
                                    if (retryCount < MAX_RETRY_COUNT) {
                                        retryCount++;
                                        // 指数退避策略，每次重试延迟时间翻倍
                                        long delay = (long) Math.pow(RETRY_DELAY_SECONDS, retryCount);
                                        System.out.println("请求失败，将在 " + delay + " 秒后重试，第 " + retryCount + " 次");
                                        return Observable.timer(delay, TimeUnit.SECONDS);
                                    }
                                }
                                // 达到最大重试次数或不是网络错误，发射错误
                                return Observable.error(throwable);
                            }
                        });
                    }
                });
    }
}
