package com.qsiny.listeners;

import com.qsiny.dto.TeacherMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

    @RabbitListener(queues = {"message_delayed_queue"})
    public void handler(TeacherMessageDto message){
        log.info("当前需要处理message:{}",message);
//        String msg = (String) message.getBody();
    }
}
