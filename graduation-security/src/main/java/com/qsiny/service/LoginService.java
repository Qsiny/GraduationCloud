package com.qsiny.service;


import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {

    UserDetails getLoginUser(String account,String voucher);
}
