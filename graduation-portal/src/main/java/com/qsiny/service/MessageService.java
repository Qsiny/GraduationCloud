package com.qsiny.service;

import com.qsiny.dto.MessageDto;
import com.qsiny.entity.ResponseResult;

public interface MessageService {

    ResponseResult<Void> sendMessage(MessageDto messageDto);
}
