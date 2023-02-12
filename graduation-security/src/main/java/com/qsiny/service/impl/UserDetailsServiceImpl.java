package com.qsiny.service.impl;

import com.qsiny.entity.CustomizeException;
import com.qsiny.entity.LoginUser;
import com.qsiny.entity.User;
import com.qsiny.mapper.MenuMapper;
import com.qsiny.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(!StringUtils.hasText(username)){
            throw new CustomizeException("凭证不可为空");
        }
        User user = userMapper.findUserByUsernameOrTel(username);
        if(user == null){
            throw new CustomizeException("用户名或密码错误");
        }
        List<String> menus = menuMapper.selectPermsByUserId(user.getUserId());

        return new LoginUser(user,menus);
    }
}
