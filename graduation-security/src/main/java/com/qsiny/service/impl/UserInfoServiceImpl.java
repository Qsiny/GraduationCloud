package com.qsiny.service.impl;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.RedisConstant;
import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.entity.CustomizeException;
import com.qsiny.entity.LoginUser;
import com.qsiny.entity.ResponseResult;
import com.qsiny.entity.User;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Override
    public ResponseResult<UserInfoResponse> userLogin(String userMessage,String encodePassword,Boolean rememberMe,Integer loginWay) {

        String password = verifyService.decode(encodePassword);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userMessage, password);
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        if(loginUser == null){
            log.info("验证失败！{}",userMessage);
            return ResponseResult.build(404,"用户名或密码错误！");
        }


        Long userId = loginUser.getUser().getUserId();
        String userName = loginUser.getUser().getUserName();
        //这里用uuid来代替id
        String uuid = JwtUtil.getUUID();
        //通过userId: 加id 放入redis中，loginUser
        redisCache.setCacheObject( RedisConstant.TOKEN_PRE+uuid,loginUser,7, TimeUnit.DAYS);
        //生成一个token返回给前端

        String token = JwtUtil.createJWT(userName, JwtUtil.ONE_DAY,uuid);
        String reFlushToken = JwtUtil.createJWT(userName,JwtUtil.SEVEN_DAYS,uuid);
        log.info("用户登陆成功:{}",userId);
        return ResponseResult.build(200,"成功",new UserInfoResponse(userId,userName,token,reFlushToken));
    }

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

        userMapper.insert(user);

        LoginUser loginUser = new LoginUser(user,null);

        //通过userId: 加id 放入redis中，loginUser
        String uuid = JwtUtil.getUUID();
        redisCache.setCacheObject( RedisConstant.TOKEN_PRE+uuid,loginUser,7, TimeUnit.DAYS);
        //生成一个token返回给前端
        String token = JwtUtil.createJWT( username, JwtUtil.ONE_DAY,uuid);
        String reFlushToken = JwtUtil.createJWT( username, JwtUtil.SEVEN_DAYS,uuid);

        return ResponseResult.build(200,"成功",new UserInfoResponse(user.getUserId(),username,token,reFlushToken));

    }

    @Override
    public String reFlushToken(String reFlushToken) {
        LoginUser loginUser = tokenService.encodeToken(reFlushToken);
        if(loginUser == null){
            throw new CustomizeException("redis用户信息丢失，请重新登陆");
        }
        String uuid;
        try {
            Claims claims = JwtUtil.parseJWT(reFlushToken);
            uuid = claims.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  JwtUtil.createJWT(loginUser.getUsername(), JwtUtil.ONE_DAY, uuid);
    }

}
