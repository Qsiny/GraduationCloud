package com.qsiny.controller;

import com.qsiny.entity.ResponseResult;
import com.qsiny.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qin
 * @date 2023/2/5 19:47
 */
@RestController
@RequestMapping("user")
public class UserController {

    @PostMapping("/login")
    public ResponseResult<String> login(User user){
        //打印一个user
        System.out.println(user);


        return ResponseResult.build(200,"成功");
    }
}
