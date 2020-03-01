package NIO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {

    public static void main(String args[]) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileInputChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileOutPutChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(3);

        while (fileInputChannel.read(byteBuffer) != -1){
            //要反转
            byteBuffer.flip();
            fileOutPutChannel.write(byteBuffer);
            //要复位 不然会一直循环出不去
            byteBuffer.clear();
        }

        fileInputStream.close();
        fileOutputStream.close();
        fileInputChannel.close();
        fileOutPutChannel.close();
    }
}
