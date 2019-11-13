package top.hengshare.user.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.hengshare.thrift.user.UserService;

/**
 * @program: micro-service
 * @description: ThriftProvider
 * @author: StivenYang
 * @create: 2019-11-08 09:28
 **/
@Component
public class ThriftProvider {

    @Value("${thrift.user.ip}")
    private String serverIp;

    @Value("${thrift.user.port}")
    private int serverPort;

    public UserService.Client getUserService() {

        //初始化服务器ip和端口
        TSocket socket = new TSocket(serverIp, serverPort, 30000);
        //初始化传输层
        TTransport transport  = new TFramedTransport(socket);
        //打开传输层
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        //设置传输协议
        TProtocol protocol = new TBinaryProtocol(transport);

        UserService.Client client = new UserService.Client(protocol);

        return client;
    }
}
