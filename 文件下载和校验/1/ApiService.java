import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 包含User的BaseResponse
     */
    @GET("users/{userId}")
    Observable<BaseResponse<User>> getUserInfo(@Path("userId") String userId);
}
    