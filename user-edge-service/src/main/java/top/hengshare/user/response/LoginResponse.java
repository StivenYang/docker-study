package top.hengshare.user.response;

import lombok.Data;

/**
 * @program: micro-service
 * @description: LoginResponse
 * @author: StivenYang
 * @create: 2019-11-11 19:46
 **/
@Data
public class LoginResponse extends Response {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
