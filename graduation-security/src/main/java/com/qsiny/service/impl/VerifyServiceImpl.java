package com.qsiny.service.impl;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.RedisConstant;
import com.qsiny.entity.CustomizeException;
import com.qsiny.service.VerifyService;
import com.qsiny.utils.KeyToolsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerifyServiceImpl implements VerifyService {

    @Resource
    private RedisCache redisCache;

    @Value("${privateKey}")
    private String privateKey;
    @Override
    public Boolean verifyPhoneCode(String encodePhonenumber, String code) {
        String phonenumber = this.decode(encodePhonenumber);
        String checksum = redisCache.getCacheObject(RedisConstant.PHONE_CODE_PRE+phonenumber);
        if(!StringUtils.hasText(checksum)){
            return false;
        }
        return checksum.equals(code);
    }

    @Override
    public Boolean createChecksum(String encodePhonenumber) {

        String phonenumber = this.decode(encodePhonenumber);
        //之后新增次数的限制
        String createChecksum = String.valueOf(System.currentTimeMillis()).substring(5);
        redisCache.setCacheObject(RedisConstant.PHONE_CODE_PRE+phonenumber,createChecksum,3, TimeUnit.MINUTES);
        log.info("验证码是：{}",createChecksum);
        return true;
    }

    @Override
    public String decode(String param){
        String message;
        try {
            message = KeyToolsUtil.decryptByPrivateKey(param, KeyToolsUtil.getPrivateKey(privateKey));
        } catch (Exception e) {
            throw new CustomizeException("信息解密错误");
        }
        return message;
    }
}
