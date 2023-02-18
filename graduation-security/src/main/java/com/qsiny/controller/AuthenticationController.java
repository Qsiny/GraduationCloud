package com.qsiny.controller;

import com.qsiny.entity.ResponseResult;
import com.qsiny.service.AuthenticationService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Resource
    private AuthenticationService authenticationService;

    @PostMapping(value = "PermissionAuthentication")
    ResponseResult<Boolean> PermissionAuthentication(@RequestBody String requestUrl){
        if(!StringUtils.hasText(requestUrl)){
            return ResponseResult.build(500,"当前url为空");
        }
        System.out.println(requestUrl);
        return ResponseResult.build(200,"成功",authenticationService.hasAuthentication(requestUrl));
    }


}
