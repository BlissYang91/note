import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {
    //ServerSocket做服务端
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            byte[] bytes =  new byte[1024*5];
            int len = inputStream.read(bytes);//从客户端读取数据
            System.out.println(new String(bytes,0,len));
            //读取文件
//            FileInputStream fileInputStream = new FileInputStream("D:\\img\\binder.jpg");
            outputStream.write("服务端发来".getBytes());
//            while ((len=fileInputStream.read(bytes))!=-1){
//                outputStream.write(bytes,0,len);
//            }
            outputStream.write(bytes,0,len);
            outputStream.flush();//发送数据到客户端

//            fileInputStream.close();
            inputStream.close();
            outputStream.close();
//            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
