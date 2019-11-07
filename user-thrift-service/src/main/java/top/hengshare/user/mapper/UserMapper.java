package top.hengshare.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.hengshare.thrift.user.UserInfo;

/**
 * @program: docker-study
 * @description: UserMapper
 * @author: StivenYang
 * @create: 2019-11-07 13:57
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

    @Select("select id, username, password, real_name as realName, mobile, email" +
            " from tb_user where id = #{id}")
    UserInfo getUserById(@Param("id") int id);

    @Insert("insert into tb_user (username, password, real_name, mobile, email)" +
            "values (#{u.username}, #{u.password}, #{u.real_name}, #{u.mobile}," +
            "#{u.email})")
    void registerUser(@Param("u") UserInfo userInfo);

    @Select("select id, username, password, real_name as realName, mobile, email" +
            " from tb_user where username = #{username}")
    UserInfo getUserByName(@Param("username") String username);
}
