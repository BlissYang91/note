import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    // 获取下载信息（包含文件URL、MD5等）
    @GET("download/info")
    Observable<DownloadInfoResponse> getDownloadInfo();
    
    // 下载文件（使用@Streaming避免大文件占用过多内存）
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
    