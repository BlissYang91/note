import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.List;

public class CompleteFileDemo {
    public static void main(String[] args) {
        // 1. 原始JSON数据
        String jsonData = "{" +
                "\"code\": 200," +
                "\"message\": \"成功\"," +
                "\"result\": [31, -117, 8, 0]," +
                "\"size\": 4" +  // 这里设置为4，因为result数组有4个元素
                "}";
        
        // 2. 解析JSON为Java对象
        ApiResponse response = parseJsonToResponse(jsonData);
        if (response == null) {
            System.err.println("JSON解析失败，程序退出");
            return;
        }
        
        // 3. 处理并写入文件
        String filePath = "data.bin";
        FileProcessor processor = new FileProcessor();
        boolean success = processor.writeAndValidate(response, filePath);
        
        // 4. 输出最终结果
        if (success) {
            System.out.println("\n===== 操作成功 =====");
            System.out.println("数据已成功写入文件: " + new File(filePath).getAbsolutePath());
        } else {
            System.out.println("\n===== 操作失败 =====");
        }
    }
    
    /**
     * 将JSON字符串解析为ApiResponse对象
     */
    private static ApiResponse parseJsonToResponse(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ApiResponse.class);
        } catch (Exception e) {
            System.err.println("解析JSON时发生错误: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 文件处理器类，负责写入数据和验证文件完整性
     */
    static class FileProcessor {
        /**
         * 将List<Integer>转换为字节数组并写入文件，然后验证文件完整性
         */
        public boolean writeAndValidate(ApiResponse response, String filePath) {
            try {
                // 获取需要处理的数据
                List<Integer> integerList = response.getResult();
                long expectedSize = response.getSize();
                
                System.out.println("准备写入的数据: " + integerList);
                System.out.println("预期文件大小: " + expectedSize + " 字节");
                
                // 转换为字节数组
                byte[] byteArray = convertToByteArray(integerList);
                System.out.println("转换后的字节数组长度: " + byteArray.length + " 字节");
                
                // 写入文件
                writeToFile(byteArray, filePath);
                System.out.println("数据已写入文件");
                
                // 验证文件完整性
                return validateFileIntegrity(filePath, expectedSize);
                
            } catch (IOException e) {
                System.err.println("文件处理出错: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        
        /**
         * 将List<Integer>转换为字节数组
         */
        private byte[] convertToByteArray(List<Integer> integerList) {
            if (integerList == null) {
                return new byte[0];
            }
            
            byte[] byteArray = new byte[integerList.size()];
            for (int i = 0; i < integerList.size(); i++) {
                byteArray[i] = integerList.get(i).byteValue();
            }
            return byteArray;
        }
        
        /**
         * 将字节数组写入文件
         */
        private void writeToFile(byte[] data, String filePath) throws IOException {
            // 使用try-with-resources确保流自动关闭
            try (FileOutputStream fos = new FileOutputStream(filePath);
                 BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                bos.write(data);
            }
        }
        
        /**
         * 验证文件大小是否与预期一致
         */
        private boolean validateFileIntegrity(String filePath, long expectedSize) {
            File file = new File(filePath);
            
            if (!file.exists()) {
                System.err.println("验证失败: 文件不存在");
                return false;
            }
            
            long actualSize = file.length();
            System.out.println("实际文件大小: " + actualSize + " 字节");
            
            if (actualSize == expectedSize) {
                System.out.println("验证成功: 文件完整");
                return true;
            } else {
                System.err.println("验证失败: 文件大小不匹配");
                return false;
            }
        }
    }
    
    /**
     * API响应数据模型类
     */
    static class ApiResponse {
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
    