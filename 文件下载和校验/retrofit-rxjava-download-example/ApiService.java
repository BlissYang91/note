import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    // 获取下载信息（包含文件URL和MD5）
    @GET("download/info")
    Observable<DownloadInfo> getDownloadInfo();
    
    // 下载文件（@Streaming用于大文件下载）
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
