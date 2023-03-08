package com.qsiny.service.impl;

import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.dto.MessageDto;
import com.qsiny.dto.TeacherMessageDto;
import com.qsiny.entity.ResponseResult;
import com.qsiny.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Override
    public ResponseResult<Void> sendMessage(MessageDto messageDto) {

//        Date submitTIme = messageDto.getSubmitTime();
        long time = messageDto.getSubmitTime().getTime();
        long now = System.currentTimeMillis();
        int diff = (int) ( time - now);
        if(diff < 0){

            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"设定时间早于系统时间");
        }

        //做操作
        TeacherMessageDto teacherMessageDto = new TeacherMessageDto();
        teacherMessageDto.setMessage("chess");
        if(judgeTimeIsToday(messageDto.getSubmitTime())){
            log.info("发送消息,时间延迟：{}",diff);

            rabbitTemplate.convertAndSend("delayed_exchange","message_delayed",teacherMessageDto,a -> {
                a.getMessageProperties().setDelay(diff);
                return a;
            });
        }else{
            log.info("之后的消息，进入db");

        }

        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"发送成功");


    }

    public boolean judgeTimeIsToday(Date localDateTime) {
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        Date start = new Date(Timestamp.valueOf(startTime).getTime());
        Date end = new Date(Timestamp.valueOf(endTime).getTime());
        //如果大于今天的开始日期，小于今天的结束日期
        return localDateTime.after(start) && localDateTime.before(end);
    }
}
