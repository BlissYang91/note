import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTest {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.1.43",8080);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("来自客户端消息".getBytes());
            outputStream.flush();
            //读取服务端消息
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            System.out.println(new String(bytes,0,len));

            outputStream.close();
            inputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
