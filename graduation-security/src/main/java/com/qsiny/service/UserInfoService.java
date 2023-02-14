package com.qsiny.service;

import com.qsiny.entity.ResponseResult;
import com.qsiny.po.UserInfoResponse;

public interface UserInfoService {

    ResponseResult<UserInfoResponse> userLogin(String userMessage, String password);

    ResponseResult<UserInfoResponse> register(String username, String password, String phonenumber, String code);
}
