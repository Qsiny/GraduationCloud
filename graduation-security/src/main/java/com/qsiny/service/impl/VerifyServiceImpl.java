package com.qsiny.service.impl;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.RedisConstant;
import com.qsiny.service.VerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerifyServiceImpl implements VerifyService {

    @Resource
    private RedisCache redisCache;
    @Override
    public Boolean verifyPhoneCode(String phonenumber, String code) {

        String checksum = redisCache.getCacheObject(RedisConstant.PHONE_CODE_PRE+phonenumber);
        if(!StringUtils.hasText(checksum)){
            return false;
        }
        return checksum.equals(code);
    }

    @Override
    public Boolean createChecksum(String phonenumber) {
        //之后新增次数的限制
        String createChecksum = String.valueOf(System.currentTimeMillis()).substring(5);
        redisCache.setCacheObject(RedisConstant.PHONE_CODE_PRE+phonenumber,createChecksum,3, TimeUnit.MINUTES);
        log.info("验证码是：{}",createChecksum);
        return true;
    }
}
