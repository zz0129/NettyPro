package netty.inBoundHandlerAndOutBoundHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器的ip地址为:"+ctx.channel().remoteAddress());
        System.out.println("收到服务器消息:"+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("client send msg");
        ctx.writeAndFlush(12345L);
    }
}
