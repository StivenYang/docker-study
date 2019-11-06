package top.hengshare.user.service;

import org.apache.thrift.TException;
import top.hengshare.thrift.user.UserInfo;
import top.hengshare.thrift.user.UserService;

public class UserServiceImpl implements UserService.Iface {
    @Override
    public UserInfo getUserById(int id) throws TException {
        return null;
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        return null;
    }

    @Override
    public void registerUser(UserInfo userInfo) throws TException {

    }
}
