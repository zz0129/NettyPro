package netty.dubboRpc.provider;

import com.sun.deploy.util.StringUtils;
import io.netty.util.internal.StringUtil;
import netty.dubboRpc.publicInterface.HelloService;

public class HelloServiceImpl implements HelloService {

    //当有消费方调用该方法时，就返回一个结果
    private static int count = 0;

    @Override
    public String hello(String msg) {
        System.out.println("接收到客户端消息：" + msg);
        if(StringUtil.isNullOrEmpty(msg)){
            return "你好客户端，我已经收到你的消息【"+msg+"】第" + (++count) + "次";
        }else {
            return "你好客户端，我已经收到你的消息";
        }
    }
}
