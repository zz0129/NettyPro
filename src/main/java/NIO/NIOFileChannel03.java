package NIO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {

    public static void main(String args[]) throws IOException {
        FileInputStream fileInputStream =  new FileInputStream("1.txt");
        FileChannel fileInputChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileOutputChannel = fileOutputStream.getChannel();

        fileOutputChannel.transferFrom(fileInputChannel, 0, fileInputChannel.size());

        fileInputStream.close();
        fileOutputStream.close();
        fileInputChannel.close();
        fileOutputChannel.close();
    }
}
