package com.qsiny.service.impl;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.RedisConstant;
import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.entity.*;
import com.qsiny.mapper.UserMapper;
import com.qsiny.po.UserInfoResponse;
import com.qsiny.service.TokenService;
import com.qsiny.service.UserInfoService;
import com.qsiny.service.VerifyService;
import com.qsiny.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;
    
    @Resource
    private VerifyService verifyService;

    @Value("${privateKey}")
    private String privateKey;

    @Resource
    private TokenService tokenService;

//    public ResponseResult<UserInfoResponse> userLogin(String userMessage,String encodePassword,Boolean rememberMe,Integer loginWay) {
//        //注明：如果是验证码登录方式 则加密的是 电话号，code不会被加密
//        //      如果是用户名密码登录，则密码加密 两者相反
//
//        //这里采用适配器 装饰模式：
//            //
//        String password = verifyService.decode(encodePassword);
//        PasswordLoginUser passwordLoginUser = null;
//        if(loginWay == 0){
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userMessage, password);
//            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//            passwordLoginUser = (PasswordLoginUser) authenticate.getPrincipal();
//        }else if(loginWay == 1){
//            CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(userMessage,encodePassword);
//            Authentication authenticate = authenticationManager.authenticate(customAuthenticationToken);
//            passwordLoginUser = (PasswordLoginUser) authenticate.getPrincipal();
//        }
//
//        if(passwordLoginUser == null){
//            log.info("验证失败！{}",userMessage);
//            return ResponseResult.build(404,"用户名或密码错误！");
//        }
//
//
//        Long userId = passwordLoginUser.getUser().getUserId();
//        String userName = passwordLoginUser.getUser().getUserName();
//        //这里用uuid来代替id
//        String uuid = JwtUtil.getUUID();
//        //通过userId: 加id 放入redis中，loginUser
//        redisCache.setCacheObject( RedisConstant.TOKEN_PRE+uuid, passwordLoginUser,7, TimeUnit.DAYS);
//        //生成一个token返回给前端
//
//        String token = JwtUtil.createJWT(userName, JwtUtil.ONE_DAY,uuid);
//        String reFlushToken = JwtUtil.createJWT(userName,JwtUtil.SEVEN_DAYS,uuid);
//        log.info("用户登陆成功:{}",userId);
//        return ResponseResult.build(200,"成功",new UserInfoResponse(userId,userName,token,reFlushToken));
//    }

    @Override
    public ResponseResult<UserInfoResponse> register(String username, String encodePassword, String encodePhonenumber, String code) {
        String password = verifyService.decode(encodePassword);
        String phonenumber = verifyService.decode(encodePhonenumber);
        //校验CODE
        if(!verifyService.verifyPhoneCode(encodePhonenumber,code)){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"验证码错误");
        }
        String encode = passwordEncoder.encode(password);
        User user = new User();
        user.setUserName(username);
        user.setPassword(encode);
        user.setPhonenumber(phonenumber);
        user.setUserType("00");

        userMapper.insert(user);

        PasswordLoginUser passwordLoginUser = new PasswordLoginUser(user,null);

        //通过userId: 加id 放入redis中，loginUser
        String uuid = JwtUtil.getUUID();
        redisCache.setCacheObject( RedisConstant.TOKEN_PRE+uuid, passwordLoginUser,7, TimeUnit.DAYS);
        //生成一个token返回给前端
        String token = JwtUtil.createJWT( username, JwtUtil.ONE_DAY,uuid);
        String reFlushToken = JwtUtil.createJWT( username, JwtUtil.SEVEN_DAYS,uuid);
        log.info("用户注册成功:{}",user);
        return ResponseResult.build(200,"成功",new UserInfoResponse(user.getUserId(),username,token,reFlushToken,user.getUserType()));

    }

    @Override
    public String reFlushToken(String reFlushToken) {
        PasswordLoginUser passwordLoginUser = tokenService.encodeToken(reFlushToken);
        if(passwordLoginUser == null){
            throw new CustomizeException("redis用户信息丢失，请重新登陆");
        }
        String uuid;
        try {
            Claims claims = JwtUtil.parseJWT(reFlushToken);
            uuid = claims.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  JwtUtil.createJWT(passwordLoginUser.getUsername(), JwtUtil.ONE_DAY, uuid);
    }

    @Override
    public ResponseResult<Void> userLogout(String token) {
        String uuid= null;
        try {
            uuid = JwtUtil.parseJWT(token).getId();
        } catch (Exception e) {
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"伪造token，不予退出");
        }
        redisCache.deleteObject(RedisConstant.TOKEN_PRE+uuid);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功");
    }

    @Override
    public ResponseResult<UserInfoResponse> userLogin(UserDetails loginUser, Boolean rememberMe) {
        if(loginUser == null){
            return ResponseResult.build(404,"用户名或密码错误！");
        }
        Long userId = null;
        String userName = null;
        String userType = null;
        if(loginUser instanceof PasswordLoginUser){
            PasswordLoginUser passwordLoginUser = (PasswordLoginUser) loginUser;
            userId = passwordLoginUser.getUser().getUserId();
            userName = passwordLoginUser.getUser().getUserName();
            userType = passwordLoginUser.getUser().getUserType();
        }else{
            CodeLoginUser codeLoginUser = (CodeLoginUser) loginUser;
            userId = codeLoginUser.getUser().getUserId();
            userName = codeLoginUser.getUser().getUserName();
            userType = codeLoginUser.getUser().getUserType();
        }

        //这里用uuid来代替id
        String uuid = JwtUtil.getUUID();
        //通过userId: 加id 放入redis中，loginUser
        redisCache.setCacheObject( RedisConstant.TOKEN_PRE+uuid,loginUser,7, TimeUnit.DAYS);
        //生成一个token返回给前端
        String token = JwtUtil.createJWT(userName, JwtUtil.ONE_DAY,uuid);
        String reFlushToken = JwtUtil.createJWT(userName,JwtUtil.SEVEN_DAYS,uuid);
        log.info("用户登陆成功:{}",userId);
        return ResponseResult.build(200,"成功",new UserInfoResponse(userId,userName,token,reFlushToken,userType));
    }

}
