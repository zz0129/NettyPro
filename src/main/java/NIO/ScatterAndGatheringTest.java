package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatterAndGatheringTest {

    public static void main(String args[]) throws IOException {

        //使用ServerSocket和Socket网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);

        //绑定端口到ServerSocket
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;

        int readLine = 0;
        while(readLine < messageLength){
            long temp = socketChannel.read(byteBuffers);
            readLine += temp;
            Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position = " + byteBuffer.position() + "limit = "
                + byteBuffer.limit()).forEach(System.out::println);
        }

        Arrays.asList(byteBuffers).stream().forEach(byteBuffer -> byteBuffer.flip());
        System.out.println("the 8 status is :" + byteBuffers[0].get(0) + byteBuffers[0].get(1) + byteBuffers[0].get(2));

        int writeLine = 0;
        while(writeLine < messageLength){
           long temp2 = socketChannel.write(byteBuffers);
            writeLine += temp2;
        }

        Arrays.asList(byteBuffers).stream().forEach(byteBuffer -> byteBuffer.clear());

        System.out.println("readLine =" + readLine + "    writeLine = " + writeLine);
    }
}
