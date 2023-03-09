package com.qsiny.service.impl;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.RedisConstant;
import com.qsiny.entity.CustomizeException;
import com.qsiny.service.VerifyService;
import com.qsiny.utils.KeyToolsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
@Slf4j
public class VerifyServiceImpl implements VerifyService {

    @Resource
    private RedisCache redisCache;

    @Value("${privateKey}")
    private String privateKey;

    @Resource
    private RabbitTemplate rabbitTemplate;
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
        Integer count = redisCache.getCacheObject(RedisConstant.PHONE_COUNT_PRE+phonenumber);
        if(count == null){
            redisCache.setCacheObject(RedisConstant.PHONE_COUNT_PRE+phonenumber,1);
        }else{
            if(count > 3){
                return false;
            }
            redisCache.setCacheObject(RedisConstant.PHONE_COUNT_PRE+phonenumber,count+1);
        }

        //之后新增次数的限制
        rabbitTemplate.convertAndSend("direct_exchange","sms",phonenumber);
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
