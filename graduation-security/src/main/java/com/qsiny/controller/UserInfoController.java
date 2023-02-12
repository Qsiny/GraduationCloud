package com.qsiny.controller;

import com.qsiny.entity.ResponseResult;
import com.qsiny.service.UserInfoService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/userLogin")
    public ResponseResult<String> userLogin(String userNameOrTel,String password){
        if(!StringUtils.hasText(userNameOrTel)||!StringUtils.hasText(password)){
            return ResponseResult.build(500,"用户名或密码为空");
        }
        return userInfoService.userLogin(userNameOrTel,password);
    }




}
