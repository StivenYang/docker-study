package top.hengshare.user.controller;

import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.hengshare.thrift.user.UserInfo;
import top.hengshare.user.config.RedisConfig;
import top.hengshare.user.dto.UserDTO;
import top.hengshare.user.response.LoginResponse;
import top.hengshare.user.response.Response;
import top.hengshare.user.thrift.ThriftProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @program: micro-service
 * @description: UserController
 * @author: StivenYang
 * @create: 2019-11-08 09:24
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ThriftProvider provider;
    @Autowired
    private RedisConfig redisConfig;

    @PostMapping("login")
    public Response login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        //1. 先验证用户名密码
        UserInfo userInfo = null;
        try {
            userInfo = provider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if (userInfo == null) {
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if (!userInfo.getPassword().equalsIgnoreCase(md5(password))) {
            return Response.USERNAME_PASSWORD_INVALID;
        }

        //2. 生成token，作为唯一标识
        String token = genToken();

        //3. 缓存用户
        redisConfig.redisTemplate(new RedisConfig().redisConnectionFactory()).opsForValue().set(token, toDTO(userInfo), 3600);

        //4. 返回用户token
        return new LoginResponse(token);
    }

    private UserDTO toDTO(UserInfo userInfo) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userInfo, userDTO);
        return userDTO;
    }

    private String genToken() {
        return RandomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);
    }

    private String RandomCode(String str, int size) {
        StringBuilder result = new StringBuilder(size);

        Random random = new Random();
        for (int i=0; i<size; i++){
            int loc = random.nextInt(str.length());
            result.append(str.charAt(loc));
        }

        return result.toString();
    }

    private String md5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(password.getBytes());

            return HexUtils.toHexString(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
