package netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String idleType = null;
            switch (idleStateEvent.state()){
                case READER_IDLE:
                    idleType = "读空闲";
                    break;
                case WRITER_IDLE:
                    idleType = "写空闲";
                    break;
                case ALL_IDLE:
                    idleType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"超时时间"+idleType);
            System.out.println("服务器做空闲处理");
        }
    }
}
