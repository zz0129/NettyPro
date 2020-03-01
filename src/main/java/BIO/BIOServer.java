package BIO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

    public static void main(String args[]) throws IOException {

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器开始监听端口");

        while (true) {
            System.out.println("开始等待客户端链接。。。");
            final Socket socket = serverSocket.accept();
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void handler(Socket socket) throws IOException {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[2048];
            while (true){
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                }else{
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("关闭client。。。。");
            try {
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
