package com.qsiny.controller;

import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.dto.LoginUserDto;
import com.qsiny.entity.ResponseResult;
import com.qsiny.po.UserInfoResponse;
import com.qsiny.service.UserInfoService;
import com.qsiny.service.VerifyService;
import com.qsiny.service.impl.CodeLoginServiceImpl;
import com.qsiny.service.impl.PasswordLoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private VerifyService verifyService;

    @Resource
    private CodeLoginServiceImpl codeLoginService;

    @Resource
    private PasswordLoginServiceImpl passwordLoginService;
    @PostMapping("/userLogin")
    public ResponseResult<UserInfoResponse> userLogin(@RequestBody LoginUserDto loginUserDto){
        if(!StringUtils.hasText(loginUserDto.getAccount())||!StringUtils.hasText(loginUserDto.getVoucher())){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"用户名或密码为空");
        }
        UserDetails userDetails;
        if(loginUserDto.getLoginWay() == 0){
            userDetails = passwordLoginService.getLoginUser(loginUserDto.getAccount(),loginUserDto.getVoucher());
        }else{
            userDetails = codeLoginService.getLoginUser(loginUserDto.getAccount(), loginUserDto.getVoucher());
        }

        return userInfoService.userLogin(userDetails,loginUserDto.getRememberMe());
    }

    @PostMapping("/userRegister")
    public ResponseResult<UserInfoResponse> userRegister(String username,String password,String phonenumber,String code){
        if(!StringUtils.hasText(username)||!StringUtils.hasText(password)
                ||!StringUtils.hasText(phonenumber)||!StringUtils.hasText(code)){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"必须填写的信息未填写");
        }
        //前端做了非空校验，后端也要做
        return userInfoService.register(username,password,phonenumber,code);
    }

    @PostMapping("/getChecksum")
    public ResponseResult<Void> getChecksum(String phonenumber){
        if(!StringUtils.hasText(phonenumber)){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"电话号码输入为空");
        }
        if(!verifyService.createChecksum(phonenumber)){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"今天以获取了3次验证码了，请明天再试");
        }
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"发送成功，验证码有效期三分钟");
    }

    @PostMapping("/reFlushToken")
    public ResponseResult<String> reFlushToken(String reFlushToken){
        if(!StringUtils.hasText(reFlushToken)){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"刷新token为空");
        }
        String token = userInfoService.reFlushToken(reFlushToken);
        log.info("刷新token成功：{}",token);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"发送成功，验证码有效期三分钟",token);
    }

    @PostMapping("/logout")
    public ResponseResult<Void> logout( String token){
        if(!StringUtils.hasText(token)){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"token为空，登出失败");
        }
        log.info("用户登出：{}",token);
        return userInfoService.userLogout(token);
    }



}
