package com.qsiny.handler;

import com.alibaba.fastjson.JSON;
import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.entity.ResponseResult;
import com.qsiny.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)  {
        //这里是 401权限错误返回

        ResponseResult<Void> responseResult = ResponseResult.build(ResponseStatusCode.UNAUTHORIZED,"暂未登陆");
        String response = JSON.toJSONString(responseResult);
        //设置response的相应属性
        WebUtils.renderString(httpServletResponse,response);
    }
}
