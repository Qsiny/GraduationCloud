package com.qsiny.interceptor;

import com.qsiny.utils.ContextUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignHeaderInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

        requestTemplate.header("authorization", ContextUtils.getToken());

    }
}

