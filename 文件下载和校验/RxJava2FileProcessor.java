import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class RxJava2FileProcessor {

    // 原始JSON数据
    private static final String JSON_DATA = "{\n" +
            "\t\"code\": 200,\n" +
            "\t\"message\": \"成功\",\n" +
            "\t\"result\": [31, -117, 8, 0],\n" +
            "\t\"size\": 4\n" +  // 这里修正为4，与实际字节数匹配
            "}";

    public static void main(String[] args) {
        String filePath = "rxjava2_output.bin";
        ObjectMapper objectMapper = new ObjectMapper();

        // 完整处理流程
        Single.just(JSON_DATA)
                // 解析JSON为对象
                .map(json -> objectMapper.readValue(json, ApiResponse.class))
                // 过滤成功的响应
                .filter(response -> response.getCode() == 200)
                // 转换为字节数组并验证大小
                .map(response -> {
                    List<Integer> resultList = response.getResult();
                    if (resultList == null) {
                        throw new IllegalArgumentException("结果列表为空");
                    }

                    // 转换List<Integer>为byte数组
                    byte[] bytes = new byte[resultList.size()];
                    for (int i = 0; i < resultList.size(); i++) {
                        bytes[i] = resultList.get(i).byteValue();
                    }

                    // 验证大小是否匹配
                    if (bytes.length != response.getSize()) {
                        throw new IllegalStateException(
                            String.format("大小不匹配: 实际=%d, 预期=%d", 
                                          bytes.length, response.getSize()));
                    }
                    return bytes;
                })
                // 写入文件
                .flatMap(bytes -> writeBytesToFile(bytes, filePath))
                // 指定线程
                .subscribeOn(Schedulers.io())       // IO操作在IO线程
                .observeOn(Schedulers.single())     // 结果处理在单线程
                // 订阅处理结果
                .subscribe(
                    success -> System.out.println("文件写入成功: " + filePath),
                    error -> {
                        System.err.println("处理失败: " + error.getMessage());
                        error.printStackTrace();
                    }
                );

        // 等待异步操作完成（仅用于演示）
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 将字节数组写入文件的RxJava2实现
     */
    private static Single<Boolean> writeBytesToFile(byte[] data, String filePath) {
        return Single.create(emitter -> {
            if (data == null || data.length == 0) {
                emitter.onError(new IllegalArgumentException("字节数组为空"));
                return;
            }

            try (OutputStream os = new FileOutputStream(filePath)) {
                os.write(data);
                os.flush();
                emitter.onSuccess(true);
            } catch (IOException e) {
                emitter.onError(new RuntimeException("文件写入失败", e));
            }
        });
    }

    /**
     * JSON对应的Java实体类
     */
    public static class ApiResponse {
        private Integer code;
        private String message;
        private List<Integer> result;
        private Long size;

        // Getters and Setters
        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Integer> getResult() {
            return result;
        }

        public void setResult(List<Integer> result) {
            this.result = result;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }
    }
}
    