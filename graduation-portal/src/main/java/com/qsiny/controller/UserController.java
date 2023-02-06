package com.qsiny.controller;

import com.qsiny.entity.LoginUser;
import com.qsiny.entity.ResponseResult;
import com.qsiny.entity.User;
import com.qsiny.utils.JwtUtil;
import com.qsiny.utils.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Qin
 * @date 2023/2/5 19:47
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private RedisCache redisCache;

    @PostMapping("/login")
    public ResponseResult<String> login(User user){
        //打印一个user
        System.out.println(user);

        ArrayList<String> roles = new ArrayList<>();
        roles.add("11");
        redisCache.setCacheObject("123456",new LoginUser(user,roles));

        String jwt = JwtUtil.createJWT(user.getUserId().toString(), user.getUserName(), JwtUtil.JWT_TTL);

        return ResponseResult.build(200,"成功",jwt);
    }
}
