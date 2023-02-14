package com.qsiny.controller;

import com.qsiny.entity.ResponseResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @PostMapping(value = "PermissionAuthentication")
    ResponseResult<Void> PermissionAuthentication(@RequestBody String requestUrl){
        if(!StringUtils.hasText(requestUrl)){
            return ResponseResult.build(500,"当前url为空");
        }
        //todo 这里对url 所拥有的权限进行限制
            System.out.println(requestUrl);
        return ResponseResult.build(200,"ceshi");
    }


}
