package com.qsiny.controller;

import com.qsiny.entity.ResponseResult;
import com.qsiny.mapper.UserMapper;
import com.qsiny.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/t1")
    public ResponseResult<String> t1(){
        //新建用户
        String encode = passwordEncoder.encode("123456");
        boolean matches = passwordEncoder.matches("123456", "$2a$10$nw6oFO9lx3SJpIg9YlFc7uTVTGGO2mN/1t5jwtbs3x3UytRmAIW4q");
        System.out.println(matches);
        log.info("user:{}",encode);
        String jwt = JwtUtil.createJWT("1");
        return ResponseResult.build(200,"成功",jwt);

    }

}
