import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    // 获取下载信息（包含文件URL和MD5校验值）
    @GET("download/info")
    Observable<DownloadInfoResponse> getDownloadInfo();
    
    // 下载文件（@Streaming 用于大文件下载，避免内存溢出）
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
    