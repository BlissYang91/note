import com.google.gson.annotations.SerializedName;

public class DownloadInfoResponse {
    @SerializedName("file_url")
    private String fileUrl;
    
    @SerializedName("file_name")
    private String fileName;
    
    @SerializedName("file_size")
    private long fileSize;
    
    @SerializedName("md5_checksum")
    private String md5Checksum;

    // Getters
    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getMd5Checksum() {
        return md5Checksum;
    }
}
    