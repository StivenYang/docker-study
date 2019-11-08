package top.hengshare.user.response;

import java.io.Serializable;

/**
 * @program: micro-service
 * @description: Response
 * @author: StivenYang
 * @create: 2019-11-08 09:59
 **/
public class Response implements Serializable {

    public static final Response USERNAME_PASSWORD_INVALID = new Response("1001", "username or password is invalid");


    private String code;
    private String message;

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
