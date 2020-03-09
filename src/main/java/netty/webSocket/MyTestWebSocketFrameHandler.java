package netty.webSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

//TextWebSocketFrame类型表示一个文本桢
public class MyTestWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器收到消息"+msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间"+ LocalDateTime.now()+msg.text()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx){
        //asLongText 得到的是唯一的
        System.out.println("handlerAdded被调用"+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        System.out.println("handlerRemoved 被调用"+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable caught){
        System.out.println("异常发生"+caught.getMessage());
        ctx.close();
    }

}
