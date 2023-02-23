package com.qsiny.service.impl;

import com.qsiny.entity.CodeLoginUser;
import com.qsiny.provider.CustomAuthenticationToken;
import com.qsiny.service.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CodeLoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Override
    public UserDetails getLoginUser(String encodePhonenumber, String code) {

        CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(encodePhonenumber,code);
        Authentication authenticate = authenticationManager.authenticate(customAuthenticationToken);
        return (CodeLoginUser) authenticate.getPrincipal();
    }
}
