import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class RxJava2FileWithMD5 {

    // 原始JSON数据，增加了一个md5字段用于校验
    private static final String JSON_DATA = "{\n" +
            "\t\"code\": 200,\n" +
            "\t\"message\": \"成功\",\n" +
            "\t\"result\": [31, -117, 8, 0],\n" +
            "\t\"size\": 4,\n" +
            "\t\"md5\": \"a321e7c987db0f96f987a654321f890\"\n" +  // 示例MD5值
            "}";

    public static void main(String[] args) {
        String filePath = "rxjava2_with_md5_output.bin";
        ObjectMapper objectMapper = new ObjectMapper();

        // 完整处理流程：解析JSON -> 转换为字节数组 -> 写入文件 -> MD5校验
        Single.just(JSON_DATA)
                // 解析JSON为对象
                .map(json -> objectMapper.readValue(json, ApiResponseWithMD5.class))
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
                    
                    // 返回包含字节数组和预期MD5的值对象
                    return new DataWithValidation(bytes, response.getSize(), response.getMd5());
                })
                // 写入文件
                .flatMap(data -> writeBytesToFile(data.bytes, filePath)
                        // 写入成功后返回数据用于校验
                        .map(success -> data))
                // 计算并验证MD5
                .flatMap(data -> calculateFileMD5(filePath)
                        .map(calculatedMd5 -> {
                            if (!calculatedMd5.equalsIgnoreCase(data.expectedMd5)) {
                                throw new SecurityException(
                                    String.format("MD5校验失败: 计算值=%s, 预期值=%s",
                                                  calculatedMd5, data.expectedMd5));
                            }
                            return true;
                        }))
                // 指定线程
                .subscribeOn(Schedulers.io())       // IO操作在IO线程
                .observeOn(Schedulers.single())     // 结果处理在单线程
                // 订阅处理结果
                .subscribe(
                    success -> System.out.println("文件写入成功且MD5校验通过: " + filePath),
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
     * 将字节数组写入文件
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
     * 计算文件的MD5值
     */
    private static Single<String> calculateFileMD5(String filePath) {
        return Single.create(emitter -> {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] buffer = new byte[8192];
                int bytesRead;
                
                try (InputStream is = new FileInputStream(filePath)) {
                    while ((bytesRead = is.read(buffer)) != -1) {
                        md.update(buffer, 0, bytesRead);
                    }
                }
                
                // 将MD5字节数组转换为十六进制字符串
                byte[] digest = md.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b));
                }
                
                emitter.onSuccess(sb.toString());
            } catch (NoSuchAlgorithmException e) {
                emitter.onError(new RuntimeException("不支持MD5算法", e));
            } catch (IOException e) {
                emitter.onError(new RuntimeException("计算MD5时读取文件失败", e));
            }
        });
    }

    /**
     * 包含数据和验证信息的值对象
     */
    private static class DataWithValidation {
        byte[] bytes;
        long expectedSize;
        String expectedMd5;

        DataWithValidation(byte[] bytes, long expectedSize, String expectedMd5) {
            this.bytes = bytes;
            this.expectedSize = expectedSize;
            this.expectedMd5 = expectedMd5;
        }
    }

    /**
     * 带MD5字段的API响应实体类
     */
    public static class ApiResponseWithMD5 {
        private Integer code;
        private String message;
        private List<Integer> result;
        private Long size;
        private String md5;  // 新增MD5字段

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

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }
    }
}
    