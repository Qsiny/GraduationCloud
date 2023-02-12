package com.qsiny.interceptor;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class LogInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        if(StringUtils.hasText(request.getHeader("traceId"))){
            MDC.put("traceId", request.getHeader("traceId"));
        }else{
            MDC.put("traceId", UUID.randomUUID().toString());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.clear();
    }
}
