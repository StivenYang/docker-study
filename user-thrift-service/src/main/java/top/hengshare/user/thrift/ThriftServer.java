package top.hengshare.user.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import top.hengshare.thrift.user.UserService;

import javax.annotation.PostConstruct;

/**
 * @program: micro-service
 * @description: ThriftServer
 * @author: StivenYang
 * @create: 2019-11-07 21:37
 **/
@Configurable
public class ThriftServer {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private UserService.Iface userService;

    @PostConstruct
    public void startThriftServer() {
        TProcessor processor = new UserService.Processor<>(userService);

        TNonblockingServerSocket socket = null;
        try {
            socket = new TNonblockingServerSocket(serverPort);
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        TNonblockingServer.Args args = new TNonblockingServer.Args(socket);
        args.processor(processor);
        args.transportFactory(new TFramedTransport.Factory());
        args.protocolFactory(new TBinaryProtocol.Factory());

        TServer server = new TNonblockingServer(args);
        server.serve();
    }
}
