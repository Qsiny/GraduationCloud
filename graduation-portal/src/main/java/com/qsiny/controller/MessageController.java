package com.qsiny.controller;

import com.qsiny.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {


    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        User user = new User();
        user.setUserType("1");
        user.setUserName("zs");
        Integer time = 10000;
        log.info("发送消息");
        rabbitTemplate.convertAndSend("delayed_exchange","sms",user,a -> {
            a.getMessageProperties().setDelay(time);
            return a;
        });
        return "okk";

    }

}
