import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int port = 8080;

    public static void main(String[] args) {
        System.out.println("Server...\n");
        Server server = new Server();
        server.init();
    }

    private void init() {
        try {
            //创建一个ServerSocket，这里可以指定连接请求的队列长度
            //new ServerSocket(port,100);意味着当队列中有100个连接请求是，如果Client再请求连接，就会被Server拒绝
            ServerSocket serverSocket = new ServerSocket(port,100);
            while (true){
                //从请求队列中取出一个连接
                Socket socketClient = serverSocket.accept();
                //处理连接
                new HandlerThread(socketClient);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    private class HandlerThread implements Runnable{
        private Socket socket;

        public HandlerThread(Socket client) {
            this.socket = client;
            new Thread(this::run).start();
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                // 读取客户端数据,读取和发送方式要和服务端相同,否则会抛 EOFException
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String clientMsg = bufferedReader.readLine();
                // 处理客户端数据
                System.out.println("客户端发来消息:" + clientMsg);
                // 向客户端回复信息
                PrintStream printStream = new PrintStream(outputStream);
                System.out.print("请输入:\t");
                String serverMsg = new BufferedReader(new InputStreamReader(System.in)).readLine();
                printStream.println(serverMsg);

                inputStream.close();
                outputStream.close();
            } catch (IOException exception) {
                System.out.println("服务端异常: " + exception.getMessage());
                exception.printStackTrace();
            }finally {
                if (null != socket){
                    try {
                        socket.close();
                    } catch (IOException exception) {
                        socket =null;
                        System.out.println("服务端 finally 异常："+exception.toString());
                        exception.printStackTrace();
                    }
                }
            }

        }
    }
}
