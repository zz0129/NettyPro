package netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {

    public static void main(String[] args){

        ByteBuf byteBuf = Unpooled.buffer(10);

        for(int i = 0; i < byteBuf.capacity(); i++){
            byteBuf.writeByte(i);
        }

        for(int j = 0; j < byteBuf.capacity(); j++){
            System.out.println(byteBuf.getByte(j));
        }
    }
}
