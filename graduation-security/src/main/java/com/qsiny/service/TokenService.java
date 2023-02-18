package com.qsiny.service;

import com.qsiny.entity.LoginUser;

public interface TokenService {

    LoginUser encodeToken(String token);

}
