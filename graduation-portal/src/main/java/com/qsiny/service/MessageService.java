package com.qsiny.service;

import com.qsiny.entity.ResponseResult;

public interface MessageService {


    <T> ResponseResult<Void> sendMessage(String exchangeName, String routingKey, Integer type, T dto, Long sendTime) ;

}
