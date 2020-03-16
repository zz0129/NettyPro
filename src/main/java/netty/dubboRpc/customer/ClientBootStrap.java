package netty.dubboRpc.customer;

import netty.dubboRpc.netty.NettyClient;
import netty.dubboRpc.provider.HelloServiceImpl;
import netty.dubboRpc.publicInterface.HelloService;

public class ClientBootStrap {

    //定义协头
    public static String providerName = "HelloServer#server#";

    public static void main(String[] args){

        //创建一个消费者
        NettyClient customer = new NettyClient();

        //创建一个代理对象
        HelloService helloService = (HelloService) customer.getBean(HelloService.class, providerName);
        //通过代理对象调用服务者提供的方法
        String res = helloService.hello("你好，Dubbo~");
        System.out.println("调用的结果res = "+res);
    }
}
