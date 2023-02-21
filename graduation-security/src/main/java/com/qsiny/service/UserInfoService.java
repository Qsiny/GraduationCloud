package com.qsiny.service;

import com.qsiny.entity.ResponseResult;
import com.qsiny.po.UserInfoResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserInfoService {

    ResponseResult<UserInfoResponse> register(String username, String password, String phonenumber, String code);

    String reFlushToken(String reFlushToken);

    ResponseResult<Void> userLogout(String token);

    ResponseResult<UserInfoResponse> userLogin(UserDetails userDetails, Boolean rememberMe);
}
