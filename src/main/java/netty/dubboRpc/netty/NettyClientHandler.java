package netty.dubboRpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;//上下文
    private String result;//返回的结果
    private String param;//访问参数，客户端调用方法时传入

    //与服务器连接创建后就会被调用(1)
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive被调用");
        context = ctx;
    }

    //收到服务器消息时就会被调用
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ChannelRead 被调用");
        result = msg.toString();
        notify();//唤醒等待线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //被代理对象调用，发消息给服务器-》等待被唤醒-》被唤醒-》返回结果
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        System.out.println("call 被调用");
        wait();
        System.out.println("call2 被调用");
        return result;
    }

    //设置参数
    void setParam(String param){
        System.out.println("param = "+ param);
        this.param = param;
    }
}
