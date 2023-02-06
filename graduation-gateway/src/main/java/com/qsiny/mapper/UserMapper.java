package com.qsiny.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsiny.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Qin
* @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
* @createDate 2023-02-05 09:48:52
* @Entity com.qsiny.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findUserByUsernameOrTel(@Param("usernameOrTel") String usernameOrTel);
}




