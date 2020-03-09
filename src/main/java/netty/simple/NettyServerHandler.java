package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.util.CharsetUtil;

/**
 * 我们自己定义的一个Handler，需要继承Netty规划好的某个HandlerAdaptor（规范）
 * 这样我们的Handler才能算作是一个Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据实例（这里读取客户端）

    /**
     * @param ctx：上线文对象，包含管道、通道、地址
     * @param msg：客户端发送的数据，默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server txt =" + ctx);
        //将msg转成byteBuffer
        //ByteBuf是netty提供的 ByteBuffer是NIO提供的
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送的数据是 = "+ byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址 = "+ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        //将数据写到管道并刷新,对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello~ 客户端", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        ctx.close();
    }
}
