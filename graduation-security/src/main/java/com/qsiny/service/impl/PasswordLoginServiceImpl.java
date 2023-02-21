package com.qsiny.service.impl;

import com.qsiny.entity.PasswordLoginUser;
import com.qsiny.service.LoginService;
import com.qsiny.service.VerifyService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PasswordLoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private VerifyService verifyService;

    @Override
    public UserDetails getLoginUser(String username,String encodePassword) {

        //解密电话
        String password = verifyService.decode(encodePassword);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return (PasswordLoginUser) authenticate.getPrincipal();
    }
}
