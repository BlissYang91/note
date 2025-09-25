/**
 * 接口通用返回模型
 * 服务器返回格式示例: {"code":200,"message":"success","data":{...}}
 */
public class BaseResponse<T> {
    private int code; // 业务状态码：200成功，503服务器繁忙（需要重试），其他为普通错误
    private String message; // 提示信息
    private T data; // 具体数据

    // getter和setter
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    // 判断是否请求成功（业务层面）
    public boolean isSuccess() {
        return code == 200;
    }

    // 判断是否需要重试（例如服务器繁忙）
    public boolean needRetry() {
        return code == 503; // 假设503表示服务器繁忙，需要重试
    }
}
    