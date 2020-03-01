package NIO.GroupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    //初始化GroupChatServer
    public GroupChatServer(){
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            //设置为非阻塞模式
            listenChannel.configureBlocking(false);
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen(){
        try {

            while(true){
                int count = selector.select(2000);
                if(count > 0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        if(selectionKey.isAcceptable()){
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }

                        if(selectionKey.isReadable()){
                            readData(selectionKey);
                        }
                        iterator.remove();
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    //读取客户端消息
    private void readData(SelectionKey selectionKey){
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(byteBuffer);
            if(count > 0){
                String msg = new String(byteBuffer.array());
                System.out.println("from 客户端" + msg);
                sendInfoToOtherClient(msg, socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "下线了");
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //转发消息给其他的客户端
    public void sendInfoToOtherClient(String msg, SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息中。。。");
        for(SelectionKey selectionKey : selector.keys()){
            Channel targetChannel = selectionKey.channel();
            if(targetChannel instanceof SocketChannel && targetChannel != selfChannel){
                SocketChannel socketChannel = (SocketChannel) targetChannel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                socketChannel.write(byteBuffer);
            }
        }

    }

    public static void main(String args[]){
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
