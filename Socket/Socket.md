
## 概述：
> ServerSocket类 TCP协议,服务器端，通过serveSocket.accept();方法获取socket实例
> Socket类 TCP协议 通过new Socket()获取实例，创建Socket对象的时候则需要声明一个IP地址和ServerSocket对象的端口号，这样才能对服务端发出连接请求
```
serverSocket.accept() 接受客户端的连接请求,并返回一个套接字.如果没有连接到客户端,线程处于阻塞状态,程序无法执行下去
一个服务器可以接受多个客户端的连接请求,但是只为第一个已连接套接字服务,只与第一个客户端通信,不会与其他的客户端通信 
如果要为多个客户端服务,让服务器接收的客户端请求(Socket socket=serverSocket.accept())处于循环中,其实就相当于有N个服务器,当然就可以与n个用户端通信

客户端上的使用

1.getInputStream方法可以得到一个输入流，客户端的Socket对象上的getInputStream方法得到输入流其实就是从服务器端发回的数据。

2.getOutputStream方法得到的是一个输出流，客户端的Socket对象上的getOutputStream方法得到的输出流其实就是发送给服务器端的数据。

服务器端上的使用

1.getInputStream方法得到的是一个输入流，服务端的Socket对象上的getInputStream方法得到的输入流其实就是从客户端发送给服务器端的数据流。

2.getOutputStream方法得到的是一个输出流，服务端的Socket对象上的getOutputStream方法得到的输出流其实就是发送给客户端的数据。

```
## Socket和浏览器通信

```
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

```
## 连接和通信
> 服务端ServerSocket：

```
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

```
> 客户端Socket

```
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

```
## Socket保持连接通信
> 客户端

```
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

```
> 服务端


```
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
```





