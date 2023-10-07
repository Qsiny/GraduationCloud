package com.qsiny.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qsiny.constant.CommonValues;
import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.dto.TeacherMessageDto;
import com.qsiny.entity.CustomizeException;
import com.qsiny.entity.DelayedMessage;
import com.qsiny.entity.ResponseResult;
import com.qsiny.mapper.DelayedMessageMapper;
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

    @Resource
    private DelayedMessageMapper delayedMessageMapper;
    @Override
    public <T> ResponseResult<Void> sendMessage(String exchangeName,String routingKey,Integer type,T dto,Long sendTime) {


//      如果是0则代表是普通消息 直接发送
        if(CommonValues.ZERO.equals(type)){
            rabbitTemplate.convertAndSend(exchangeName,routingKey,dto);
        }else{
            //延迟发送
            sendDelayedMessage(exchangeName,routingKey,dto,sendTime);
        }

        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"发送成功");

    }

    private <T> void sendDelayedMessage(String exchangeName,String routingKey,T dto,Long sendTime){

        Date submitTIme = new Date(sendTime);
        long time = submitTIme.getTime();
        long now = System.currentTimeMillis();
        int diff = (int) ( time - now);
        if(diff < 0){
            throw new CustomizeException("发送延迟消息时间早于系统时间");
        }

        //这里先判断是否今天 不然定时任务会有冲突
        //做操作
        TeacherMessageDto teacherMessageDto = new TeacherMessageDto();
        teacherMessageDto.setMessage("chess");
        if(judgeTimeIsToday(submitTIme)){
            log.info("发送消息,时间延迟：{}",diff);
            rabbitTemplate.convertAndSend(exchangeName,routingKey,dto,a -> {
                a.getMessageProperties().setDelay(diff);
                return a;
            });
        }else{
            log.info("之后的消息，进入db,{}",dto);
            DelayedMessage delayedMessage = new DelayedMessage();
            delayedMessage.setMessageValues(JSONObject.toJSONString(dto));
            delayedMessage.setSendTime(new Date(sendTime));
            delayedMessage.setExchangeName(exchangeName);
            delayedMessage.setRoutingKey(routingKey);
            delayedMessageMapper.insert(delayedMessage);

        }
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
