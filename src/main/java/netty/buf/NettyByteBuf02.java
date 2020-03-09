package netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyByteBuf02 {

    public static void main(String[] args){

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world", CharsetUtil.UTF_8);
        if(byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            System.out.println(new String(content, CharsetUtil.UTF_8));
        }
    }
}
