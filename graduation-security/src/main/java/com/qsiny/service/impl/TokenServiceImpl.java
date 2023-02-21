package com.qsiny.service.impl;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.RedisConstant;
import com.qsiny.entity.CustomizeException;
import com.qsiny.entity.PasswordLoginUser;
import com.qsiny.service.TokenService;
import com.qsiny.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisCache redisCache;
    @Override
    public PasswordLoginUser encodeToken(String token) {
        //解析token
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        }catch (Exception e){
            throw new CustomizeException("token解析异常");
        }
        String uuid = claims.getId();
        if (uuid == null) {
            throw new CustomizeException("从token中无法解析ID");
        }
        //从redis拿到userInfo
        return redisCache.getCacheObject(RedisConstant.TOKEN_PRE+uuid);

    }
}
