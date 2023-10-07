package com.qsiny.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qsiny.entity.User;
import com.qsiny.mapper.UserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserUtils {

    @Resource
    private UserMapper userMapper;


    public User findUserById(String userId){
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
    }
}
