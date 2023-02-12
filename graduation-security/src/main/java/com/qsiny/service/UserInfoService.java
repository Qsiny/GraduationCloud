package com.qsiny.service;

import com.qsiny.entity.ResponseResult;

public interface UserInfoService {

    ResponseResult<String> userLogin(String userMessage, String password);

    ResponseResult<String> register(String username, String password, String phonenumber, String code);
}
