package com.qsiny.service;

import com.qsiny.entity.PasswordLoginUser;

public interface TokenService {

    PasswordLoginUser encodeToken(String token);

}
