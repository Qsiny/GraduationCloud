package com.qsiny.serivice.impl;


import com.qsiny.entity.CustomizeException;
import com.qsiny.entity.LoginUser;
import com.qsiny.entity.User;
import com.qsiny.mapper.MenuMapper;
import com.qsiny.mapper.UserMapper;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Qin
 * @date 2023/2/5 16:13
 */
@Service
public class UserDetailsServiceImpl  {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;


    public LoginUser findByUsername(String username) {
        //和往常一样即可
        User user = userMapper.findUserByUsernameOrTel(username);
        if(user == null){
            throw new CustomizeException("用户名或密码错误");
        }

        //从数据库中查询用户权限
        List<String> menus = menuMapper.selectPermsByUserId(user.getUserId());
        return new LoginUser(user,menus);
    }
}
