import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    public static final int port = 8080;
    public static final String host = "localhost";
    public static void main(String[] args) {
        System.out.println("Client Start...");
        while (true){
            Socket socket = null;
            try{
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                socket = new Socket(host,port);

                //读取服务器端数据
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                //向服务器端发送数据
                PrintStream printStream = new PrintStream(outputStream);
                System.out.println("向服务端发送：\t");
                String str =  new BufferedReader(new InputStreamReader(System.in)).readLine();
                printStream.println(str);

                String ret = bufferedReader.readLine();
                System.out.println("服务端返回："+ret);
                // 如接收到 "OK" 则断开连接
                if ("OK".equals(ret)) {
                    System.out.println("客户端将关闭连接");
                    Thread.sleep(500);
                    break;
                }
                outputStream.close();
                inputStream.close();
            }catch (Exception e){
                System.out.println("客户端异常"+e.toString());
            }finally {
                if(null != socket){
                    try {
                        socket.close();
                    } catch (IOException exception) {
                        socket = null;
                        System.out.println("客户端 finally 异常:"+exception.toString());
                        exception.printStackTrace();
                    }
                }
            }
        }

    }
}
