import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            byte[] bytes =  new byte[1024];
            int len = inputStream.read(bytes);//从客户端读取数据
            System.out.println(new String(bytes,0,len));
            outputStream.write("我是服务端".getBytes());
            outputStream.flush();

            outputStream.close();
            inputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
