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
    
    // getter 和 setter 方法
    public String getFileUrl() {
        return fileUrl;
    }
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public long getFileSize() {
        return fileSize;
    }
    
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
    public String getMd5Checksum() {
        return md5Checksum;
    }
    
    public void setMd5Checksum(String md5Checksum) {
        this.md5Checksum = md5Checksum;
    }
}
    