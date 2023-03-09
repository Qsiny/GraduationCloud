package com.qsiny.listeners;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SmsListener {

    @Resource
    private RedisCache redisCache;

    @RabbitListener(queues = {"sms_direct_queue"})
    public void handler(String phonenumber){
        String createChecksum = randomNum();

        redisCache.setCacheObject(RedisConstant.PHONE_CODE_PRE+phonenumber,createChecksum,3, TimeUnit.MINUTES);
        log.info("验证码是：{}",createChecksum);
    }

    private String randomNum(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            Random random = new Random();
            int num = random.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }
}
