import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * API服务接口
 */
public interface ApiService {
    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 包含用户信息的Observable
     */
    @GET("users/{userId}")
    Observable<UserResponse> getUserInfo(@Path("userId") String userId);
}
