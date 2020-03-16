package netty.dubboRpc.publicInterface;

//服务消费方和服务提供方都需要的
public interface HelloService {
    String hello(String msg);
}
