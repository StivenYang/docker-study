package top.hengshare.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: micro-service
 * @description: UserDTO
 * @author: StivenYang
 * @create: 2019-11-11 19:43
 **/
@Data
public class UserDTO implements Serializable {

    private int id;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String realName;

}
