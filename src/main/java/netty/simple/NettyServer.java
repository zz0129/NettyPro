package netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        //创建BossGroup、workerGroup线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务端启动对象、配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程进行设置，设置两个线程组
            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class) //使用NioServerSocketChannel作为服务器的通道实现
                .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到连接数
                .childOption(ChannelOption.SO_KEEPALIVE,true) //设置保持活动连接的状态
                .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象(匿名对象)
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                }); //给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("服务器已经准备好了。。");

            //绑定一个端口，并生成一个future对象
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
