package com.qsiny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qsiny.entity.CodeLoginUser;
import com.qsiny.entity.CustomizeException;
import com.qsiny.entity.User;
import com.qsiny.mapper.MenuMapper;
import com.qsiny.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CodeUserDetailsServiceImpl implements UserDetailsService {


    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String phonenumber) throws UsernameNotFoundException {
        if(!StringUtils.hasText(phonenumber)){
            throw new CustomizeException("凭证不可为空");
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda()
                .eq(User::getPhonenumber, phonenumber));

        if(user == null){
            throw new CustomizeException("用户名或密码错误");
        }
        List<String> menus = menuMapper.selectPermsByUserId(user.getUserId());

        return new CodeLoginUser(user,menus);
    }

}
