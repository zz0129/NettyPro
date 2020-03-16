package netty.dubboRpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.dubboRpc.customer.ClientBootStrap;
import netty.dubboRpc.provider.HelloServiceImpl;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        System.out.println("msg="+msg);
        //客户端在调用服务器时，会规定一个协议，这里协议为已特定字符串开头
        if(msg.toString().startsWith(ClientBootStrap.providerName)){
            System.out.println("111111111");
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#"))+1);
            ctx.channel().writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable caught){
        caught.printStackTrace();
        ctx.close();
    }

}
