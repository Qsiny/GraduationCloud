package com.qsiny.service.impl;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.RedisConstant;
import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.entity.LoginUser;
import com.qsiny.entity.ResponseResult;
import com.qsiny.entity.User;
import com.qsiny.mapper.UserMapper;
import com.qsiny.service.UserInfoService;
import com.qsiny.service.VerifyService;
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
    private RedisCache redisCache;
    
    @Resource
    private VerifyService verifyService;
    @Override
    public ResponseResult<String> userLogin(String userMessage,String password) {


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userMessage, password);
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        if(loginUser == null){
            return ResponseResult.build(404,"用户名或密码错误！");
        }


        String userId = String.valueOf(loginUser.getUser().getUserId());
        String userName = loginUser.getUser().getUserName();
        //通过userId: 加id 放入redis中，loginUser
        redisCache.setCacheObject( RedisConstant.AUTH_PRE+userId,loginUser,7, TimeUnit.DAYS);
        //生成一个token返回给前端
        String token = JwtUtil.createJWT(userId, userName, JwtUtil.JWT_TTL);

        return ResponseResult.build(200,"成功",token);
    }

    @Override
    public ResponseResult<String> register(String username, String password, String phonenumber, String code) {
        
        //校验CODE
        if(!verifyService.verifyPhoneCode(phonenumber,code)){
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
        redisCache.setCacheObject( RedisConstant.AUTH_PRE+2,loginUser,7, TimeUnit.DAYS);
        //生成一个token返回给前端
        String token = JwtUtil.createJWT("2", username, JwtUtil.JWT_TTL);

        return ResponseResult.build(200,"成功",token);

    }
    
}
