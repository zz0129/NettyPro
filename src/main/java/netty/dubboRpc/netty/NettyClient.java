package netty.dubboRpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static NettyClientHandler clientHandler;
    private static int count = 0;

    //编写方法使用代理模式，获取一个代理对象
    public Object getBean(final Class<?> serviceClass, final String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
            new Class<?>[] {serviceClass}, (proxy, method, args) -> {
            System.out.println("(proxy, method, args)进入第"+(++count)+"次");
            //{}部分代码，每调用一次hello就会调用一次
            if(clientHandler ==  null){
                initClient();
            }
            System.out.println("args0 = " + args[0]);
            //providerName为协议头，设置要发给服务端的参数
            clientHandler.setParam(providerName+args[0]);
            return executor.submit(clientHandler).get();
        });
    }

    //初始化客户端
    private static void initClient() throws InterruptedException {
        clientHandler = new NettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(clientHandler);
                    }
                });
            bootstrap.connect("127.0.0.1", 7000).sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
