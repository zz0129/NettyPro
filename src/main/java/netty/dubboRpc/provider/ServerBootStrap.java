package netty.dubboRpc.provider;

import netty.dubboRpc.netty.NettyServer;

//启动一个服务
public class ServerBootStrap {

    public static void main(String[] args) throws InterruptedException {
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
