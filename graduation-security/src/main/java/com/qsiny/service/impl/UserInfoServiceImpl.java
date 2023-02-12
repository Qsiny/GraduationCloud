package com.qsiny.service.impl;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.UserConstant;
import com.qsiny.entity.LoginUser;
import com.qsiny.entity.ResponseResult;
import com.qsiny.mapper.MenuMapper;
import com.qsiny.mapper.UserMapper;
import com.qsiny.service.UserInfoService;
import com.qsiny.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RedisCache redisCache;
    @Override
    public ResponseResult<String> userLogin(String userMessage,String password) {


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userMessage, password);
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        LoginUser loginUser1 = (LoginUser) authenticate.getPrincipal();
        if(loginUser1 == null){
            return ResponseResult.build(404,"用户名或密码错误！");
        }


        String userId = String.valueOf(loginUser1.getUser().getUserId());
        String userName = loginUser1.getUser().getUserName();
        //通过userId: 加id 放入redis中，loginUser
        redisCache.setCacheObject( UserConstant.AUTH_PRE+userId,loginUser1,7, TimeUnit.DAYS);
        //生成一个token返回给前端
        String token = JwtUtil.createJWT(userId, userName, JwtUtil.JWT_TTL);

        return ResponseResult.build(200,"成功",token);
    }
}
