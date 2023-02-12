package com.qsiny.service;

import com.qsiny.entity.ResponseResult;

public interface UserInfoService {

    ResponseResult<String> userLogin(String userMessage, String password);
}
