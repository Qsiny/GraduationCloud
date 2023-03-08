package com.qsiny.controller;

import com.qsiny.dto.MessageDto;
import com.qsiny.entity.ResponseResult;
import com.qsiny.entity.User;
import com.qsiny.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String msg = "ceshi";
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

    @PostMapping("/sendMessage")
    public ResponseResult<Void> sendMessage(@RequestBody MessageDto messageDto){
        //制定这样的策略，如果时间超过了当前这天，则放入数据库，否则立即写入消息队列
        System.out.println(messageDto);
        return messageService.sendMessage(messageDto);
    }

}
