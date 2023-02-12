package com.qsiny.handler;

import com.alibaba.fastjson2.JSON;
import com.qsiny.entity.ResponseResult;
import com.qsiny.utils.WebUtils;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //这里是 401权限错误返回

        ResponseResult<Void> responseResult = ResponseResult.build(HttpStatus.SC_UNAUTHORIZED,"暂未登陆");
        String response = JSON.toJSONString(responseResult);
        //设置response的相应属性
        WebUtils.renderString(httpServletResponse,response);
    }
}
