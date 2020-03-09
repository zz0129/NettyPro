package netty.groupChat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //表示连接建立，一旦连接第一个被执行，将当前channel加入channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "已离线\n");
        System.out.println("ChannelGroup size:"+channelGroup.size());
    }

    //表示channel处于活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(ctx.channel().remoteAddress() + "上线了。。。");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        System.out.println(ctx.channel().remoteAddress() + "离线了。。。");
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if(ch != channel ){
                ch.writeAndFlush("[客户]"+channel.remoteAddress()+"发送了消息："+msg + "\n");
            }else {
                ch.writeAndFlush("[自己]发送了消息："+msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable caught){
        ctx.close();
    }
}
